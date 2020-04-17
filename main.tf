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