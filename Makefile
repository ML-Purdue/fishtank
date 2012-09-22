.PHONY: all clean

BINDIR=./bin/

all: Visualizer

Visualizer: | $(BINDIR)
	javac -d bin/ -cp bin/ src/*/*.java

run:
	java -cp bin/ graphics.Visualizer

$(BINDIR):
	mkdir -p $(BINDIR)

clean:
	rm -rf $(BINDIR)
