package org.example.service;

import org.example.domain.User;
import org.example.exceptions.SessionException;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {

	User findById(Long id);

	User wrapRequest(final HttpServletRequest request) throws SessionException;
}
