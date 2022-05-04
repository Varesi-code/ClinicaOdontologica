terraform {
    required_version = ">= 0.12.0"

    required_providers {
        aws = {
            # Especificamos desde donde queremos descargar el binario:
        source = "hashicorp/aws"
        # Le decimos que solo permitirá:
        # b. la versión del binario del provider 3.20.0 (con cierta restricción)
        version = "~> 3.20.0"
        }
    }
}
provider "aws" {

shared_credentials_file = "~/.aws/credentials"

region = var.aws_region
}