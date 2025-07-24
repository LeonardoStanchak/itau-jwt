provider "aws" {
  region = "us-east-1"
}

resource "aws_ecs_cluster" "jwt_validator_cluster" {
  name = "jwt-validator-cluster"
}
