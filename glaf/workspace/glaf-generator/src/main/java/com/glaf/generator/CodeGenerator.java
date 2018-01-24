package com.glaf.generator;

import java.util.Map;

import com.glaf.core.domain.TableDefinition;

public interface CodeGenerator {

	String process(TableDefinition tableDefinition, Map<String, Object> context);

}
