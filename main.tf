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
  key_name = "QualificationProject"
  tags = {
    Name = "Web System"
  }

  provisioner "remote-exec" {
    connection {
      type = "ssh"
      user = "ec2-user"
      host = aws_instance.instance.public_ip
      private_key = file("/var/lib/jenkins/workspace/secret.pem")
    }
    inline = [
      "sudo yum install git -y"
    ]
  }
}

resource "aws_security_group" "allow_tls" {
    name        = "allow_tls"
    description = "Allow TLS inbound traffic"
    vpc_id      = "${aws_vpc.main.id}"

    ingress {
      # TLS (change to whatever ports you need)
      from_port     = 443
      to_port       = 443
      protocol      = "tcp"
      cidr_blocks   = ["0.0.0.0/24"]
    }

    egress {
      from_port     = 0
      to_port       = 0
      protocol      = "-1"
      cidr_blocks   = ["0.0.0.0/16"]
    }
}

    #SSH
  resource "aws_security_group_rule" "allow_ssh" {
    type = "ingress"
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/32"]
    security_group_id = aws_security_group.allow_tls.id
  }

    #HTTP
  resource "aws_security_group_rule" "allow_http" {
    type              = "ingress"
    from_port         = 80
    to_port           = 80
    protocol          = "tcp"
    cidr_blocks       = ["0.0.0.0/16"]
    security_group_id = aws_security_group.allow_tls.id
  }

    #Port_8080
  resource "aws_security_group_rule" "allow_8080" {
    type              = "ingress"
    from_port         = 8080
    to_port           = 8080
    protocol          = "tcp"
    cidr_blocks       = ["0.0.0.0/16"]
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

#Configure Subnet
resource "aws_subnet" "public_subnet_eu_central_1b" {
  vpc_id                  = "${aws_vpc.main.id}"
  cidr_block              = "10.0.0.0/24"
  map_public_ip_on_launch = true
  availability_zone = "eu-central-1b"
  tags = {
  	Name =  "Subnet az 1b"
  }
}

resource "aws_subnet" "private_1_subnet_eu_central_1b" {
  vpc_id                  = "${aws_vpc.main.id}"
  cidr_block              = "10.0.0.0/24"
  availability_zone = "eu-central-1b"
  tags = {
  	Name =  "Subnet private 1 az 1b"
  }
}

resource "aws_subnet" "private_2_subnet_eu_central_1b" {
  vpc_id                  = "${aws_vpc.main.id}"
  cidr_block              = "10.0.0.0/24"
  availability_zone = "eu-central-1b"
  tags = {
  	Name =  "Subnet private 2 az 1b"
  }
}

# Associate subnet public_subnet_eu_west_1a to public route table
resource "aws_route_table_association" "public_subnet_eu_central_1b_association" {
    subnet_id = "${aws_subnet.public_subnet_eu_central_1b.id}"
    route_table_id = "${aws_vpc.main.main_route_table_id}"
}

# Associate subnet private_1_subnet_eu_west_1a to private route table
resource "aws_route_table_association" "pr_1_subnet_eu_central_1b_association" {
    subnet_id = "${aws_subnet.private_1_subnet_eu_central_1b.id}"
    route_table_id = "${aws_route_table.private_route_table.id}"
}

# Associate subnet private_2_subnet_eu_west_1a to private route table
resource "aws_route_table_association" "pr_2_subnet_eu_central_1b_association" {
    subnet_id = "${aws_subnet.private_2_subnet_eu_central_1b.id}"
    route_table_id = "${aws_route_table.private_route_table.id}"
}

#Load Balancer

resource "aws_lb" "testLB" {
    name = "Test-LB-SSH"
    internal = false
    load_balancer_type = "network"
    subnets = ["${aws_subnet.public_subnet_eu_central_1b.id}"]

    //enable_deletion_protection = true

    tags = {
      Environment = "production"
    }
}

resource "aws_alb_target_group" "testTarget" {
  name = "tf-example-lb-tg"
  port = 22
  protocol = "TCP"
  vpc_id = "${aws_vpc.main.id}"
}

resource "aws_alb_target_group_attachment" "test" {
    target_group_arn = "${aws_alb_target_group.testTarget.arn}"
    target_id = "${aws_instance.instance.id}"
    port = 22
}

resource "aws_lb_listener" "testLB" {
    load_balancer_arn = "${aws_lb.testLB.arn}"
    port = "80"
    protocol = "TCP"

    default_action {
      type = "forward"
      target_group_arn = "${aws_alb_target_group.testTarget.arn}"
    }
}

#Internet Gateway
resource "aws_internet_gateway" "gw" {
  vpc_id = "${aws_vpc.main.id}"
  tags = {
        Name = "InternetGateway"
    }
}

resource "aws_nat_gateway" "nat" {
    allocation_id = "${aws_eip.main_eip.id}"
    subnet_id = "${aws_subnet.public_subnet_eu_central_1b.id}"
    depends_on = ["aws_internet_gateway.gw"]
}

resource "aws_route_table" "private_route_table" {
    vpc_id = "${aws_vpc.main.id}"

    tags = {
        Name = "Private route table"
    }
}

resource "aws_route" "internet_access" {
  route_table_id         = "${aws_vpc.main.main_route_table_id}"
  destination_cidr_block = "0.0.0.0/0"
  gateway_id             = "${aws_internet_gateway.gw.id}"
}

resource "aws_route" "private_route" {
	route_table_id  = "${aws_route_table.private_route_table.id}"
	destination_cidr_block = "0.0.0.0/0"
	nat_gateway_id = "${aws_nat_gateway.nat.id}"
}

resource "aws_eip" "main_eip" {
  vpc      = true
  depends_on = ["aws_internet_gateway.gw"]
}
