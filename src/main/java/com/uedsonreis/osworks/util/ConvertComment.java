package com.uedsonreis.osworks.util;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uedsonreis.osworks.api.dto.comment.CommentInput;
import com.uedsonreis.osworks.api.dto.comment.CommentOutput;
import com.uedsonreis.osworks.domain.model.Comment;

@Component
public class ConvertComment {
	
	@Autowired
	private ModelMapper modelMapper;

	public Comment input(CommentInput input) {
		return this.modelMapper.map(input, Comment.class);
	}
	
	public List<Comment> input(List<CommentInput> listInput) {
		return listInput.stream().map(ci -> this.input(ci)).collect(Collectors.toList());
	}
	
	public CommentOutput output(Comment comment) {
		return this.modelMapper.map(comment, CommentOutput.class);
	}
	
	public List<CommentOutput> output(List<Comment> list) {
		return list.stream().map(comment -> this.output(comment)).collect(Collectors.toList());
	}

}