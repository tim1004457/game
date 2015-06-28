# -*-coding:utf-8-*-
__author__ = 'bj'
import string
import os


def createJava(file_name):
    file_obj = open(file_name)
    className = file_name[0:file_name.index('.xt')]
    packageName = ""
    nameArray = []
    descArray = []
    try:
        list_content = file_obj.readlines()
        for content in list_content:
            content = content.replace("\n", "")
            content = content.replace("\t", "")
            content = content.replace(" ", "")
            idx = (content.index("="))
            name = content[0:idx]
            value = content[idx + 1:content.__len__()].replace("\n", "")
            if name == "packageName":
                packageName = value
            elif name != 'className':
                nameArray.append(name)
                descArray.append(value)
    finally:
        file_obj.close()

    fileContent = "package " + packageName + ";\n\n";
    fileContent += "public enum " + className + " {\n"
    for i in range(0, nameArray.__len__()):
        if i == nameArray.__len__() - 1:
            fileContent += ("\t" + nameArray[i] + "(\"" + descArray[i] + "\");\n")
        else:
            fileContent += ("\t" + nameArray[i] + "(\"" + descArray[i] + "\"),\n")
    fileContent += "\tprivate String desc;\n\n"
    fileContent += "\t" + className + '(String desc) {\n\t\tthis.desc = desc;'
    fileContent += '\n\t}\n\n';
    fileContent += "\tpublic String toString() {\n\t\treturn desc;\n\t}\n"

    fileContent += "\tpublic static " + className + " valueOf(int value) {\n"
    fileContent += "\t\tswitch (value) {\n";
    for i in range(0, nameArray.__len__()):
        fileContent += "\t\t\tcase " + str(i) + ":\n";
        fileContent += "\t\t\t\treturn " + nameArray[i] + ";\n";
    fileContent += "\t\t\tdefault:\n\t\t\t\treturn " + nameArray[
        nameArray.__len__() - 1] + ";\n\t\t\t}\n\t\t}\n";


    fileContent += "\tpublic static " + className + " toValue(String value) {\n"
    fileContent += "\t\tvalue = value.toLowerCase();\n";
    for i in range(0, nameArray.__len__()):
        fileContent += "\t\tif (value.equals("+nameArray[i]+".name().toLowerCase())){\n";
        fileContent += "\t\t\treturn "+nameArray[i]+";\n";
        fileContent += "\t\t}\n";
    fileContent+="\t\treturn "+nameArray[nameArray.__len__()-1]+";\n"
    fileContent+="\t}\n"

    fileContent += "}\n"
    outFile = open(className + ".java", "wb")
    outFile.write(fileContent );
    outFile.flush()
    outFile.close()


dir = os.listdir("./")
for f in dir:
    if (f.endswith(".xt")):
        createJava(f)
