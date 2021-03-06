export IMAGE_TAG=local

start:
	@echo "Gradle Build"
	@./gradlew assemble

	@echo "Docker Build"
	@eval $$(minikube docker-env) && docker build -t illine/gateway:${IMAGE_TAG} .

	@echo "Generating configs and Starting Service"
	@ansible-playbook -i .ansible/inventories/local --vault-password-file=.ansible/ansible_vault_local.txt -e env=local .ansible/main.yaml

stop:
	@echo "Removing and Stopping Service"
	@kubectl delete all -l app=gateway
	@kubectl delete cm -l app=gateway
	@eval $$(minikube docker-env) && docker rmi illine/gateway:${IMAGE_TAG}