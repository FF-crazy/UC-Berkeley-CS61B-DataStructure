# 定义变量
JAVAC = javac
JAVA = java
SRC_DIR = .
BIN_DIR = ./.Release

# 默认目标
default: help

# 查找指定的Java文件并编译运行
%:
	@find $(SRC_DIR) -name "$*.java" | while read src; do \
		echo "Compiling $$src..." && \
		$(JAVAC) -d $(BIN_DIR) $$src && \
		$(JAVA) -cp $(BIN_DIR) $*; \
	done

# 帮助信息
help:
	@echo "使用方法: make <filename>"
	@echo "例如: make Main 将编译 Main.java 并运行 Main.class"

# 清理生成的文件
clean:
	rm -rf $(BIN_DIR)

.PHONY: default help clean