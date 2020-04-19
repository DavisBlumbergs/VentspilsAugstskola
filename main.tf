provider "aws" {
  region = "eu-central-1"
  access_key = var.secrets["access_key"]
  private_key = var.secrets["private_key"]
}

resource "aws_key_pair" "key_pair" {
  key_name = "QualificationProject"
  public_key = var.secrets["public_key"]
}

resource "aws_instance" "instance" {
  ami = "ami-076431be05aaf8080"
  instance_type = "t2.micro"
  key_name = "${aws_key_pair.key_pair}"
}

//Security Groups
resource "aws_security_group" "allow_tls" {
  name = "allow_tls"
  description = "Allow TLS inbound traffic"
  vpc_id = "${aws_vpc.main.id}"

  ingress {
    from_port = 443
    to_port = 443
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group_rule" "allow_ssh" {
  type = "ingress"
  from_port = 22
  to_port = 22
  protocol = "tcp"
  cidr_blocks = ["0.0.0.0/0"]
  security_group_id = aws_security_group.allow_tls.id
}

resource "aws_security_group_rule" "allow_http" {
  type              = "ingress"
  from_port         = 80
  to_port           = 80
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.allow_tls.id
}

resource "aws_vpc" "main" {
  cidr_block = "0.0.0.0/0"
  enable_dns_support = true
  enable_dns_hostnames = true
  tags = {
    Name = "MainVPC"
  }
}

resource "aws_subnet" "public_subnet_eu_central_1b" {
  vpc_id                  = "${aws_vpc.main.id}"
  cidr_block              = "0.0.0.0/0"
  map_public_ip_on_launch = true
  availability_zone = "eu-central-1b"
  tags = {
  	Name =  "Subnet az 1b"
  }
}


/*
resource "aws_security_group_rule" "allow_mysql" {
  type = "ingress"
  from_port = 3306
  to_port = 3306
  protocol = "tcp"
  cidr_blocks = ["0.0.0.0/0"]
}
*/



