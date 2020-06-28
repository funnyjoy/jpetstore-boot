package com.jpetstore.jpetstore.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.descriptor.JspPropertyGroupDescriptor;
import javax.servlet.descriptor.TaglibDescriptor;

import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;

public class MyJspConfigDescriptor implements JspConfigDescriptor {

	private Collection<JspPropertyGroupDescriptor> jspPropertyGroups = new LinkedHashSet<JspPropertyGroupDescriptor>();

	private Collection<TaglibDescriptor> taglibs = new HashSet<TaglibDescriptor>();

	@Override
	public Collection<JspPropertyGroupDescriptor> getJspPropertyGroups() {
		JspPropertyGroup newPropertyGroup = new JspPropertyGroup();
		newPropertyGroup.addUrlPattern("*.jsp");
		newPropertyGroup.setElIgnored("false");
		newPropertyGroup.setPageEncoding("UTF-8");
		newPropertyGroup.setScriptingInvalid("false");
		newPropertyGroup.addIncludePrelude("/WEB-INF/views/common/include.jsp");
		// You can add more configurations as you wish!
		JspPropertyGroupDescriptorImpl jspDescriptor = new JspPropertyGroupDescriptorImpl(newPropertyGroup);
		jspPropertyGroups.add(jspDescriptor);
		return jspPropertyGroups;
	}

	@Override
	public Collection<TaglibDescriptor> getTaglibs() {
		return taglibs;
	}

}