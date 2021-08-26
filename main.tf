provider "aws" {
  region = "eu-central-1"
  access_key = ""
  secret_key = ""
}

resource "aws_key_pair" "key_pair" {
  key_name = "QualificationProject"
  public_key = ""
}

resource "aws_instance" "instance" {
  ami = "ami-076431be05aaf8080"
  instance_type = "t2.micro"
  key_name = "QualificationProject"
  vpc_security_group_ids = [aws_security_group.ingress-all-test.id]
  associate_public_ip_address = "true"
  tags = {
    Name = "Web System"
  }
  subnet_id = aws_subnet.subnet1.id

  provisioner "remote-exec" {
    connection {
      type = "ssh"
      user = "ec2-user"
      host = aws_instance.instance.public_ip
      private_key = file("/var/lib/jenkins/workspace/secret.pem")
    }
    inline = [
      "whoami",
      "sudo yum install git -y",
      "sudo amazon-linux-extras install docker -y",
      "sudo usermod -a -G docker ec2-user",
      "sudo curl -L https://github.com/docker/compose/releases/download/1.20.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose",
      "sudo chmod +x /usr/local/bin/docker-compose",
      "sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose",
      "cd /home/ec2-user/",
      "git clone https://DavisBlumbergs:Iddqdidqkfa1@github.com/DavisBlumbergs/VentspilsAugstskola.git",
      "cd VentspilsAugstskola",
      "git checkout web",
      "cd demo",
      "sudo systemctl start docker.service",
      "echo gonna try again",
      "sudo /usr/bin/docker-compose up",
      "exit"
    ]
  }
}

resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
  enable_dns_hostnames = true
  enable_dns_support = true
}

resource "aws_eip" "ip-test-env" {
  instance = aws_instance.instance.id
  vpc      = true
}

resource "aws_internet_gateway" "test-env-gw" {
  vpc_id = aws_vpc.main.id
}

resource "aws_subnet" "subnet1" {
  cidr_block = cidrsubnet(aws_vpc.main.cidr_block, 3, 1)
  vpc_id = aws_vpc.main.id
  availability_zone = "eu-central-1a"
}

resource "aws_security_group" "ingress-all-test" {
  name = "allow-all-sg"
  vpc_id = aws_vpc.main.id
  ingress {
    cidr_blocks = ["0.0.0.0/0"]
    from_port = 22
    to_port = 22
    protocol = "tcp"
  }
  ingress {
    cidr_blocks = ["0.0.0.0/0"]
    from_port = 8080
    to_port = 8080
    protocol = "tcp"
  }
  ingress{
    cidr_blocks = ["0.0.0.0/0"]
    from_port = 80
    to_port = 80
    protocol = "tcp"
  }
  ingress {
    cidr_blocks = ["0.0.0.0/0"]
    from_port = 3306
    to_port = 3306
    protocol = "tcp"
  }
  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_route_table" "route-table-test-env" {
  vpc_id = aws_vpc.main.id
route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.test-env-gw.id
  }
}
resource "aws_route_table_association" "subnet-association" {
  subnet_id      = aws_subnet.subnet1.id
  route_table_id = aws_route_table.route-table-test-env.id
}

