#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

IMAGE_NAME="${IMAGE_NAME:-gestion-inventarios-api}"
IMAGE_TAG="${IMAGE_TAG:-latest}"
KUBECONFIG="${KUBECONFIG:-$ROOT_DIR/.kube-local/config}"
export KUBECONFIG

if [[ ! -f "$KUBECONFIG" ]]; then
  echo "Error: falta el kubeconfig local en $KUBECONFIG" >&2
  echo "Copie la configuración del clúster a .kube-local/config." >&2
  exit 1
fi

if ! command -v docker >/dev/null 2>&1; then
  echo "Error: Docker no está disponible en el runner." >&2
  exit 1
fi

if ! command -v kubectl >/dev/null 2>&1; then
  echo "Error: kubectl no está disponible en el runner." >&2
  exit 1
fi

CURRENT_CONTEXT="$(kubectl config current-context)"
case "$CURRENT_CONTEXT" in
  kind-*|k3d-*)
    if command -v kind >/dev/null 2>&1 && [[ "$CURRENT_CONTEXT" == kind-* ]]; then
      kind load docker-image "$IMAGE_NAME:$IMAGE_TAG" --name "${CURRENT_CONTEXT#kind-}"
    elif command -v k3d >/dev/null 2>&1 && [[ "$CURRENT_CONTEXT" == k3d-* ]]; then
      k3d image import "$IMAGE_NAME:$IMAGE_TAG"
    fi
    ;;
  minikube)
    if command -v minikube >/dev/null 2>&1; then
      minikube image load "$IMAGE_NAME:$IMAGE_TAG"
    fi
    ;;
esac

kubectl apply -k k8s/overlays/local
kubectl rollout status deployment/postgres --timeout=180s
kubectl rollout restart deployment/gestion-inventarios-api
kubectl rollout status deployment/gestion-inventarios-api --timeout=180s
kubectl get deployments,pods,services
