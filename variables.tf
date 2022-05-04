variable "aws_region" {
  description = "us-east-1"
  type = string
  default = "us-east-1"
}

variable "aws_vpc_cidr" {
  description = "Nuestro Security Group"
  type = string
  default = "10.0.0.0/24"
}

variable "public_subnet" {
  description = "subnet con acceso a internet"
  type = string
  default = "10.0.0.128/26"
}

variable "private_subnet" {
  description = "subnet sin acceso a internet"
  type = string
  default = "10.0.0.192/26"  
}

