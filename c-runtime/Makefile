############
# Programs #
############

MKDIR := mkdir
MKDIR_P := mkdir -p

#############
# Locations #
#############

# make SRCDIR and INCLUDEDIR available to doxygen
export SRCDIR := $(realpath ./src)
export INCLUDEDIR := $(realpath ./include)
export TESTDIR := $(realpath ./test)
export INCLUDEDIRTEST := $(realpath ./test/include)
export SRCDIRTEST := $(realpath ./test/src)

export C_FILES_TEST := $(wildcard ./test/src/test/*.c ./test/src/test/*/*.c* ./src/*.c ./src/*/*.c*)
export C_FILES_TEST := $(filter-out ./src/program.c, $(C_FILES_TEST))
export C_FILES := $(wildcard ./src/*.c ./src/*/*.c*)
export C_FILES := $(filter-out ./src/program.c, $(C_FILES))
export DOXYFILE := $(realpath ./Doxyfile)

# make DOCDIR available to doxygen
export DOCDIR := $(abspath ./doc)
export BINDIR := $(abspath ./bin)
export OUTDIR := $(abspath ./out)

#################
# Default Flags #
#################

CFLAGS ?= -Wall -Werror -pedantic
CFLAGS += -std=c11 -I$(INCLUDEDIR)
CFLAGS += -ffile-prefix-map=$(SRCDIR)=.
# make getline() (see man getline(3)) available
CFLAGS += -D_POSIX_C_SOURCE=200809L

ifdef DEBUG
CFLAGS += -ggdb -DMINI_PYTHON_DEBUG=1 -Og
LDFLAGS += -ggdb -Og
else
# disable assertions
# not for now CFLAGS += -DNDEBUG
endif

#################
# Default g++ Flags #
#################

CXXFLAGS ?= -std=c++14

###########
# Targets #
###########

BIN := $(BINDIR)/program

# supports up to two directories, add more '*/' elements in a wildcard to support deeper nesting
SRC := $(wildcard $(SRCDIR)/*.c) $(wildcard $(SRCDIR)/*/*.c) $(wildcard $(SRCDIR)/*/*/*.c)

OBJECT := $(patsubst $(SRCDIR)/%.c,$(OUTDIR)/%.o,$(SRC))
DEPENDENCY := $(OBJECT:.o=.d)

#########
# Rules #
#########

.PHONY: all
all: $(BIN)

ifneq ($(MAKECMDGOALS),clean)
$(DEPENDENCY) : $(OUTDIR)/%.d: $(SRCDIR)/%.c
	$(if $(wildcard $(dir $@)),,$(MKDIR_P) $(dir $@))
	$(CC) $(CFLAGS) -MM $< > $@
-include $(DEPENDENCY)
endif

$(BIN): $(OBJECT)
	$(if $(wildcard $(dir $@)),,$(MKDIR_P) $(dir $@))
	$(CC) $(LDFLAGS) $^ $(LOADLIBS) $(LDLIBS) -o $@

$(OBJECT) : $(OUTDIR)/%.o: $(SRCDIR)/%.c
	$(if $(wildcard $(dir $@)),,$(MKDIR_P) $(dir $@))
	$(CC) $(CPPFLAGS) $(CFLAGS) -c -o $@ $<

.PHONY: run
run: $(BIN)
	./bin/program

.PHONY: clean
clean:
	$(RM) -r $(BIN) $(OBJECT) $(DEPENDENCY) $(wildcard $(DOCDIR)/*)
	$(RM) $(TESTDIR)/main

.PHONY: doc
doc:
	$(if $(wildcard $(dir $@)),,$(MKDIR_P) $(dir $@))
	doxygen $(DOXYFILE)

.PHONY: lint
lint:
	clang-tidy --checks=*,-bugprone-reserved-identifier,-cert-dcl37-c,-cert-dcl51-cpp,-altera-struct-pack-align $(SRC)

.PHONY: test
test:
	d=$$(date +%s)\
	; make -f $(TESTDIR)/Systemtests/makefile \
	&& echo "Build took $$(($$(date +%s)-d)) seconds"


.PHONY: test_clean
test_clean:
	make -f $(TESTDIR)/Systemtests/makefile clean

.PHONY: test_echo
test_echo:
	make -f $(TESTDIR)/Systemtests/makefile echo

.PHONY: test_run
test_run:
	make -f $(TESTDIR)/Systemtests/makefile run

.PHONY: test_target
test_target:
	d=$$(date +%s)\
	; 	make -f $(TESTDIR)/Systemtests/makefile test_greater_equal_op \
	&& echo "Build took $$(($$(date +%s)-d)) seconds"

.PHONY: run_target
run_target:
	./test/Systemtests/ComparisonOps/TestGreaterEqualOp.out
