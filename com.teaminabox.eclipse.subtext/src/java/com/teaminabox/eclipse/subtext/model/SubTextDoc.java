package com.teaminabox.eclipse.subtext.model;


public final class SubTextDoc {
	private String name;
	private PointCut pointCut;
	private String documentation;

	public interface Closure {
		void execute(SubTextDoc subTextDoc);
	}

	public SubTextDoc(String name, PointCut pointCut, String documentation) {
		this.name = name;
		this.pointCut = pointCut;
		this.documentation = documentation;
	}

	public String getDocumentation() {
		return documentation;
	}
	
	public PointCut getPointCut() {
		return pointCut;
	}
	
	public String getName() {
		return name;
	}
}
