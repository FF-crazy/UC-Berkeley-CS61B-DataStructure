# 定义变量
JAVAC = javac
JAVA = java
SRC_DIR = .
BIN_DIR = ../.Release

# 默认目标
default: help

# 编译并运行指定的Java文件
%: $(SRC_DIR)/%.java
	echo "Compiling $<..."
	$(JAVAC) -d $(BIN_DIR) $<
	$(JAVA) -cp $(BIN_DIR) $*

# 帮助信息
help:
	@echo "使用方法: make <filename>"
	@echo "例如: make Main 将编译 Main.java 并运行 Main.class"

# 清理生成的文件
clean:
	rm -rf $(BIN_DIR)

.PHONY: default help clean