#!/env/bin/ python3
# -*- coding: utf-8 -*-


import os

c_directory = '/home/terrific/Java_Projects/test_client/src/minecraft/xyz/terrifictable/command/commands'
m_directory = '/home/terrific/Java_Projects/test_client/src/minecraft/xyz/terrifictable/module/modules'
modules = []
commands = []

def idk(dir):
    for filename in os.listdir(dir):
        f = os.path.join(dir, filename)

        if os.path.isfile(f):
            commands.append(
                f.replace(dir + "\\", "").replace(".java", ""))
        else:
            idk(f)


def length(m): return len(m)


def get_commands():
    idk(c_directory)
    commands.sort(key=length, reverse=True)

    return commands


def idk(dir):
    for filename in os.listdir(dir):
        f = os.path.join(dir, filename)

        if os.path.isfile(f):
            modules.append(
                f.replace(dir + "\\", "").replace(".java", ""))
        else:
            idk(f)


def length(m): return len(m)


def get_modules():
    idk(m_directory)
    modules.sort(key=length, reverse=True)

    return modules

  

def gen_table():
    table = ""

    modules = get_modules()
    commands = get_commands()

    max_len = len(modules[0]) + 3

    table += "Modules" + (" " * (max_len - len("Modules"))) + "| Commands\n"
    dash = ("-" * ((len("Modules" +
                        (" " * (max_len - len("Modules"))) + "| Commands")))) + "\n"

    res = ""
    for i, idk in enumerate(dash):
        if i == (len("Modules") + (max_len - len("Modules"))):
            res += "|"
        else:
            res += idk

    table += res
    for x, module in enumerate(modules):
        try:
            command = commands[x]
        except:
            command = ""

        table += module + (" " * (max_len - len(module))) + \
            "| " + command + "\n"

    return table


version = input("Version: ")
news = input("News: ")
image_1 = input("Image 1 url: ")
image_2 = input("Image 2 url: ")

with open("./README.md", "w") as f:
    f.write("## Version " + version + "\n\n")
    f.write("<img src=\"" + image_1 + "\" />\n")
    f.write("<img src=\"" + image_2 + "\" />\n\n")
    f.write(news + "<br>\n\n")
    f.write(gen_table())

