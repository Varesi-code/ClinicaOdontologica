# Creamos nuestro VPC
resource "aws_vpc" "Main" { 
    # usamos el bloque "resource", el "provider element" y una "etiqueta"
    # para indicarle a terraform que queremos crear un recurso de tipo "aws_vpc"
    # y que este recurso se llamará "Main".
        cidr_block = var.aws_vpc_cidr
        tags = {
            Name = "Main"
        }
}

#Creamos un Internet Gateway para conectar nuestro VPC con Internet
# y lo asociamos a nuestro VPC

resource "aws_internet_gateway" "IGW" {
    #vamos a conocer el id una vez que ya se haya creado
    vpc_id = aws_vpc.Main.id
    tags = {
        Name = "IGW"
    }
}

#Creamos la subred publica
resource "aws_subnet" "public_subnets" {
    vpc_id = aws_vpc.Main.id
    cidr_block = var.public_subnet
    tags = {
        Name = "public_subnet"
    }
}

#Creamos la subred privada
resource "aws_subnet" "private_subnets" {
    vpc_id = aws_vpc.Main.id
    cidr_block = var.private_subnet
    tags = {
        Name = "private_subnet"
    }
}

#Creamos el route table
resource "aws_route_table" "Public_RT" {
    vpc_id = aws_vpc.Main.id
    route {
        
        cidr_block = "0.0.0.0/0"
        #Declaramos el trafico desde la subred publica hacia el gateway
        #de internet
        gateway_id = aws_internet_gateway.IGW.id
    }
    tags = {
        Name = "Tabla de Ruteo Publica"
    }
}

    #tabla de ruteo para la subred privada
resource "aws_route_table" "Private_RT" {
    vpc_id = aws_vpc.Main.id
    route {
    cidr_block = "0.0.0.0/0"
    #Declaramos el trafico desde la subred privada hacia el gateway
    # a internet mediante NAT Gateway
    gateway_id = aws_internet_gateway.IGW.id        
    }
    tags = {
        Name = "Tabla de Ruteo Privada"
    }
}

#Asociamos la subred publica al route table
resource "aws_route_table_association" "public_RT_association" {
    subnet_id = aws_subnet.public_subnets.id
    route_table_id = aws_route_table.Public_RT.id
}

#Asociamos la subred privada al route table
resource "aws_route_table_association" "private_RT_association" {
    subnet_id = aws_subnet.private_subnets.id
    route_table_id = aws_route_table.Private_RT.id
}
        
resource "aws_eip" "NAT_EIP" {
    vpc = true
    tags = {
        Name = "NATcon elastic IP"
    }
}

#Creamos el NAT Gateway
resource "aws_nat_gateway" "NAT_GW" {
    allocation_id = aws_eip.NAT_EIP.id
    subnet_id = aws_subnet.private_subnets.id
    tags = {
        Name = "NAT Gateway alocada en la subred publica"
    }
}

