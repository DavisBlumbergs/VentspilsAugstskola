provider "aws" {
  region = "eu-central-1"
  access_key = "AKIATWISYI3JRALWW6FO"
  secret_key = "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"
}

resource "aws_key_pair" "key_pair" {
  key_name = "QualificationProject"
  public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC8l2k4ta8Q9g2XBRM0zYUbiTBwSzTTt4+7M72qG7StrSTIU6elplXg5Qn9k1mMKAHMq3pq3czF/yDGQgaCllwslu40/92JFblENQ2WlMd4b2VT/wQ2fbpJoxSBA9ncYStuMD8i/no9cMefNCO5nmDtvd81UZKzmN9SUKA5pyLj8C0uPL9+RdTGRx8f9wD3dN1v5rM//bKe7diaxS4XxW68Rm0cy3mlgtLKXe7Ap6xvm7ru1cQIyfIcoyX6kXddTU23lx286tCibQtWA5dLr5kzjVyLdMA3b/otvGf2b6URdzAiaaqN/jpK2ptyen9mPwRq5KRyiBo3GUCMv9O10z79"
}

resource "aws_instance" "instance" {
  ami = "ami-076431be05aaf8080"
  instance_type = "t2.micro"
  key_name = "${aws_key_pair.key_pair.key_name}"

  provisioner "remote-exec" {
    inline = [
      "sudo yum install git -y"
    ]
  }
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
  cidr_block = "10.0.0.0/16"
  enable_dns_support = true
  enable_dns_hostnames = true
  tags = {
    Name = "MainVPC"
  }
}

resource "aws_subnet" "public_subnet_eu_central_1b" {
  vpc_id                  = "${aws_vpc.main.id}"
  cidr_block              = "10.0.0.0/24"
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



