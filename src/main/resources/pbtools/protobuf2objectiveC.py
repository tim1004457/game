#! /usr/bin/python
import os,sys,time
import codecs

global className
global headerLines
global implLines
global enumName
enumName = ""

	
def handleLine(line):
	items = line.split()
	if len(items) >= 2:
		if items[0] == "package" or items[0] == "option" or items[0] == "import" or items[0].startswith("/" or items[0].startswith("*")):
			return
		childType = ""
		if items[0] == "enum":
			global enumName
			enumName = items[1]
			beginEnum(className)	
		elif items[0] == "message":
			global className
			className = items[1]
			if className.endswith("{"):
				className = className[0 : len(className) - 1]
			beginClass(className + "Model")
		else:
			global enumName
			if len(enumName) > 0:
				name = items[0]
				value = items[2]
				writeEnumValue(name, value[0:len(value) - 1])
			else:
				if items[0] == "required" or items[0] == "optional":
					del items[0]
				type = items [0];
				name = items[1];
				index = findIndex(items)
				if index.endswith(";"):
					index = index[0 : len(index) - 1]
				if type == "repeated":
					name = items[2]
					childType = items[1]
				writeProperty(name, type, childType, index)
	elif len(items) > 0:
		if items[0] == "}":
			if len(enumName) > 0:
				endEnum()
			else:
				endClass(className + "Model")
	
	return
	
def findIndex(items):
	index = -1;
	for item in items:
		sepIndex = item.find(";")
		if sepIndex > 0:
			index = item[0:sepIndex]
	return index
	
def beginEnum(enumName):
	print ("begin enum %s" % (enumName))
	global headerLines
	global implLines
	headerLines = []
	implLines = []
	dateStr = time.strftime("%Y-%m-%d", time.localtime())
	headerLines.append("//\n//  %s.h\n//  EightTrip\n//\n//  Created by script on %s.\n//  Copyright (c) %s 8pig. All rights reserved.\n//\n\n" % (enumName, dateStr, dateStr[0:4]))
	headerLines.append("typedef enum %s{\n" % (enumName))
	return
	
def endEnum():
	global enumName
	global headerLines
	global implLines
	headerLines.append("}%s;\n" %(enumName))
	headerFile = open("%s.h" %(enumName), "w")
	headerFile.writelines(headerLines)
	headerLines = []
	headerFile.close()
	enumName = ""
	return
	
	
def writeEnumValue(name, value):
		global headerLines
		if len(value) > 0:
			headerLines.append("    %s = %s,\n" % (name, value))
		else:
			headerLines.append("    %s,\n" % (name))

def beginClass(className):
	print ("begin class %s" % (className))
	global headerLines
	global implLines
	headerLines = []
	implLines = []
	headerLines.append("@interface %s : NSObject\n\n" % (className))
	implLines.append("#import \"%s.h\"\n" % (className))
	implLines.append("#import \"PBCoder.h\"\n\n")
	implLines.append("@implementation %s\n\n" % (className))
	implLines.append("PBCODER_TABLE_BEGIN(%s)\n" % (className))
	return
	
def endClass(className):
	global headerLines
	global implLines
	headerLines.append("\n@end")
	implLines.append("PBCODER_TABLE_END(%s)\n\n@end" % (className))
	dateStr = time.strftime("%Y-%m-%d", time.localtime())
	headerLines.insert(0, "//\n//  %s.h\n//  EightTrip\n//\n//  Created by script on %s.\n//  Copyright (c) %s 8pig. All rights reserved.\n//\n\n" % (className, dateStr, dateStr[0:4]))
	implLines.insert(0, "//\n//  %s.h\n//  EightTrip\n//\n//  Created by script on %s.\n//  Copyright (c) %s 8pig. All rights reserved.\n//\n\n" % (className, dateStr, dateStr[0:4]))
	
	headerFile = open("%s.h" %(className), "w")
	implFile = open("%s.mm" % (className), "w")
	
	headerFile.writelines(headerLines)
	implFile.writelines(implLines)
	
	headerLines = []
	implLines = []
	headerFile.close()
	implFile.close()
	
	return

def writeProperty(name, type, childType, index):
	infos = getTypeInfo(type)
	global headerLines
	global implLines
	
	headerLines.append("@property (nonatomic, %s) %s%s;\n" % (infos[0], infos[1], name))
	
	if len(infos[4]) > 0:
		headerLines.insert(0, "#import \"%s.h\"\n" % (infos[4]))
	
	if len(childType) > 0:
		childType += "Model"
		implLines.insert(0, "#import \"%s.h\"\n" % (childType))
	pbProperty = "    %s(%s, %s, %s, %s, %s)\n" % (infos[2], className + "Model", name, infos[3], childType, index)
	implLines.append(pbProperty.replace(" ,", ""))
	return
	
def getTypeInfo(type):
	infos = []
	assignType = "strong"
	typeName = type + "Model *"
	pbProperty = "PBCODER_OBJ_PROPERTY"
	pbType = type + "Model"
	importClass = ""
	
	if type == "int32":
		assignType = "assign"
		typeName = "int "
		pbProperty = "PBCODER_INT32_PROPERTY"
		pbType = ""
	elif type == "bool":
		assignType = "assign"
		typeName = "bool "
		pbProperty = "PBCODER_BOOL_PROPERTY"
		pbType = ""
	elif type == "double":
		assignType = "assign"
		typeName = "double "
		pbProperty = "PBCODER_DOUBLE_PROPERTY"
		pbType = ""
	elif type == "float":
		assignType = "assign"
		typeName = "float "
		pbProperty = "PBCODER_FLOAT_PROPERTY"
		pbType = ""
	elif type == "uin32":
		assignType = "assign"
		typeName = "unsigned int "
		pbProperty = "PBCODER_UINT32_PROPERTY"
		pbType = ""
	elif type == "int64":
		assignType = "assign"
		typeName = "long long "
		pbProperty = "PBCODER_INT64_PROPERTY"
		pbType = ""
	elif type == "enum":
		assignType = "assign"
		typeName = type + " "
		pbProperty = "PBCODER_ENUM_PROPERTY"
		pbType = ""
	elif type == "uint64":
		assignType = "assign"
		typeName = "unsigned long long "
		pbProperty = "PBCODER_UINT64_PROPERTY"
		pbType = ""
	elif type == "string":
		assignType = "copy"
		typeName = "NSString *"
		pbType = "NSString"
	elif type == "bytes":
		typeName = "NSData *"
		pbType = "NSData"
	elif type == "repeated":
		typeName = "NSArray *"
		pbType = "NSArray"
		pbProperty = "PBCODER_UNPACKED_CONTAINER_PROPERTY"
	else:
		importClass = type + "Model"
		
		
	infos.append(assignType)
	infos.append(typeName)
	infos.append(pbProperty)
	infos.append(pbType)
	infos.append(importClass)
	return infos
		
	
	

#Read file
inFile = codecs.open(sys.argv[1], "r", "utf-8")
allLines = inFile.readlines()


for eachLine in allLines:
	handleLine(eachLine)
	
inFile.close()


