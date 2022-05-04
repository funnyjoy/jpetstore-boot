# 쿠버네티스 작업

## EKS 클러스터 생성
```
#eksctl create cluster --name funnyjoy-eks-cluster --region ap-northeast-2 --nodegroup-name standard-workers --node-type t3.large --nodes 2 --nodes-min 2 --nodes-max 3

eksctl create cluster --name funnyjoy-eks-cluster --ssh-access=true --ssh-public-key=~/.ssh/id_rsa.pub --region ap-northeast-2 --nodegroup-name funnyjoy-eks-cluster-ng --node-type t3a.xlarge --nodes 2 --nodes-min 2 --nodes-max 3

eksctl get nodegroup --cluster funnyjoy-eks-cluster
```

## EKS 클러스터 Scale 조정
```
eksctl scale nodegroup --cluster=funnyjoy-eks-cluster --nodes=2 --name=standard-workers --nodes-min=2 --nodes-max=3
```

## EKS 클러스터 삭제
```
eksctl delete cluster --name=funnyjoy-eks-cluster
```

## Metric 서버 설치
```
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
```

## Application 배포
```
k apply -f ./configserver/deployment.yaml
k apply -f ./configserver/service.yaml

k apply -f ./jpetstoredb/deployment.yaml
k apply -f ./jpetstoredb/service.yaml
k apply -f ./orderdb/deployment.yaml
k apply -f ./orderdb/service.yaml
k apply -f ./productdb/deployment.yaml
k apply -f ./productdb/service.yaml

k apply -f ./rabbitmq/deployment.yaml
k apply -f ./rabbitmq/service.yaml
k apply -f ./redis/deployment.yaml
k apply -f ./redis/service.yaml

k apply -f ./elasticsearch/service.yaml
k apply -f ./elasticsearch/deployment.yaml

k apply -f ./scouterserver/deployment.yaml
k apply -f ./scouterserver/service.yaml
k apply -f ./scouterserver/service2.yaml

k apply -f ./kibana/deployment.yaml
k apply -f ./kibana/service.yaml
k apply -f ./zipkin/deployment.yaml
k apply -f ./zipkin/service.yaml

k apply -f ./eurekaserver/deployment.yaml
k apply -f ./eurekaserver/service.yaml
k apply -f ./eurekaserver2/deployment.yaml
k apply -f ./eurekaserver2/service.yaml

k apply -f ./jpetstore/deployment.yaml
k apply -f ./jpetstore/service.yaml
k apply -f ./jpetstore/hpa.yaml
k apply -f ./order/deployment.yaml
k apply -f ./order/service.yaml
k apply -f ./order/hpa.yaml
k apply -f ./product/deployment.yaml
k apply -f ./product/service.yaml
k apply -f ./product/hpa.yaml
k apply -f ./zuulserver/deployment.yaml
k apply -f ./zuulserver/service.yaml
k apply -f ./zuulserver/hpa.yaml
```

## Cluster Autoscaler 설치
```
aws iam create-policy \
     --policy-name AmazonEKSClusterAutoscalerPolicy \
     --policy-document file://cluster-autoscaler-policy.json
{
    "Policy": {
        "PolicyName": "AmazonEKSClusterAutoscalerPolicy", 
        "PermissionsBoundaryUsageCount": 0, 
        "CreateDate": "2022-04-29T17:31:11Z", 
        "AttachmentCount": 0, 
        "IsAttachable": true, 
        "PolicyId": "ANPA335FSTDIBDYIT3VFJ", 
        "DefaultVersionId": "v1", 
        "Path": "/", 
        "Arn": "arn:aws:iam::815854164176:policy/AmazonEKSClusterAutoscalerPolicy", 
        "UpdateDate": "2022-04-29T17:31:11Z"
    }
}

eksctl utils associate-iam-oidc-provider --region=ap-northeast-2 --cluster=funnyjoy-eks-cluster --approve

eksctl create iamserviceaccount \
  --cluster=funnyjoy-eks-cluster \
  --namespace=kube-system \
  --name=cluster-autoscaler \
  --attach-policy-arn=arn:aws:iam::815854164176:policy/AmazonEKSClusterAutoscalerPolicy \
  --override-existing-serviceaccounts \
  --approve

curl -o cluster-autoscaler-autodiscover.yaml https://raw.githubusercontent.com/kubernetes/autoscaler/master/cluster-autoscaler/cloudprovider/aws/examples/cluster-autoscaler-autodiscover.yaml

kubectl apply -f cluster-autoscaler-autodiscover.yaml

kubectl annotate serviceaccount cluster-autoscaler \
  -n kube-system \
  eks.amazonaws.com/role-arn=arn:aws:iam::815854164176:role/eksctl-funnyjoy-eks-cluster-addon-iamservice-Role1-1QB3AATUQGNYG

kubectl patch deployment cluster-autoscaler \
  -n kube-system \
  -p '{"spec":{"template":{"metadata":{"annotations":{"cluster-autoscaler.kubernetes.io/safe-to-evict": "false"}}}}}'

kubectl -n kube-system edit deployment.apps/cluster-autoscaler

kubectl set image deployment cluster-autoscaler \
  -n kube-system \
  cluster-autoscaler=k8s.gcr.io/autoscaling/cluster-autoscaler:v1.21.2

```