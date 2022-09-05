JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
classes = \
		  Main.java \
		  Token.java \
		  Scanner.java \
		  Parser.java \
		  IR.java \
default: classes

clean:
	$(RM) *.class

%.class : %.java
    $(JAVAC) $<

build:
	jar -cvf lab1.jar IR.java IR.class Main.java Main.class Parser.java parser.class Scanner.java Scanner.class Token.java Token.class
	
