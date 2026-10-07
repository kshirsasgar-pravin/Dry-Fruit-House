package com.DryFruitHouse.controller;

import java.io.IOException;
import java.util.List;

import com.DryFruitHouse.entity.Category;
import com.DryFruitHouse.service.CategoryService;
import com.DryFruitHouse.service.impl.CategoryServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String ACTION_LIST = "list";
	private static final String ACTION_ACTIVE = "active";
	private static final String ACTION_GET = "get";
	private static final String ACTION_GET_BY_NAME = "getByName";
	private static final String ACTION_ADD = "add";
	private static final String ACTION_UPDATE = "update";
	private static final String ACTION_DELETE = "delete";

	private CategoryService categoryService;

	@Override
	public void init() throws ServletException {
		categoryService = new CategoryServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null || action.isBlank()) {
			action = ACTION_LIST;
		}

		try {
			switch (action) {

				case ACTION_LIST:
					listCategories(request, response);
					break;

				case ACTION_ACTIVE:
					getActiveCategories(request, response);
					break;

				case ACTION_GET:
					getCategoryById(request, response);
					break;

				case ACTION_GET_BY_NAME:
					getCategoryByName(request, response);
					break;

				default:
					sendBadRequest(response, "Invalid category action");
			}

		} catch (Exception e) {
			handleException(request, response, e);
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null || action.isBlank()) {
			sendBadRequest(response, "Category action is required");
			return;
		}

		try {
			switch (action) {

				case ACTION_ADD:
					addCategory(request, response);
					break;

				case ACTION_UPDATE:
					updateCategory(request, response);
					break;

				case ACTION_DELETE:
					deleteCategory(request, response);
					break;

				default:
					sendBadRequest(response, "Invalid category action");
			}

		} catch (Exception e) {
			handleException(request, response, e);
		}
	}

	private void listCategories(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		List<Category> categories = categoryService.getAllCategories();

		request.setAttribute("categories", categories);

		request.getRequestDispatcher("/WEB-INF/views/category/list.jsp")
				.forward(request, response);
	}

	private void getActiveCategories(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		List<Category> categories = categoryService.getActiveCategories();

		request.setAttribute("categories", categories);

		request.getRequestDispatcher("/WEB-INF/views/category/list.jsp")
				.forward(request, response);
	}

	private void getCategoryById(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		Long categoryId = parseLongParameter(
				request,
				"categoryId"
		);

		if (categoryId == null) {
			sendBadRequest(response, "Valid categoryId is required");
			return;
		}

		Category category = categoryService.getCategoryById(categoryId);

		if (category == null) {
			response.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					"Category not found"
			);
			return;
		}

		request.setAttribute("category", category);

		request.getRequestDispatcher("/WEB-INF/views/category/view.jsp")
				.forward(request, response);
	}

	private void getCategoryByName(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String categoryName = request.getParameter("categoryName");

		if (categoryName == null || categoryName.isBlank()) {
			sendBadRequest(response, "Category name is required");
			return;
		}

		Category category =
				categoryService.getCategoryByName(categoryName.trim());

		if (category == null) {
			response.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					"Category not found"
			);
			return;
		}

		request.setAttribute("category", category);

		request.getRequestDispatcher("/WEB-INF/views/category/view.jsp")
				.forward(request, response);
	}

	private void addCategory(
			HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {


		 String categoryName =
				request.getParameter("categoryName");

		String description =
				request.getParameter("description");

		String categoryImage =
				request.getParameter("categoryImage");

		if (categoryName == null || categoryName.isBlank()) {
			sendBadRequest(response, "Category name is required");
			return;
		}

		Category category = new Category(
				categoryName.trim(),
				description,
				categoryImage
		);

		boolean added = categoryService.addCategory(category);

		if (added) {
			response.sendRedirect(
					request.getContextPath()
							+ "/category?action=list"
			);
		} else {
			response.sendError(
					HttpServletResponse.SC_CONFLICT,
					"Category could not be added"
			);
		}
	}

	private void updateCategory(
			HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {

		Long categoryId = parseLongParameter(
				request,
				"categoryId"
		);

		String categoryName =
				request.getParameter("categoryName");

		String description =
				request.getParameter("description");

		String categoryImage =
				request.getParameter("categoryImage");

		String activeParameter =
				request.getParameter("isActive");

		if (categoryId == null) {
			sendBadRequest(response, "Valid categoryId is required");
			return;
		}

		if (categoryName == null || categoryName.isBlank()) {
			sendBadRequest(response, "Category name is required");
			return;
		}

		Category category = new Category();

		category.setCategoryId(categoryId);
		category.setCategoryName(categoryName.trim());
		category.setDescription(description);
		category.setCategoryImage(categoryImage);

		if (activeParameter != null) {
			category.setIsActive(
					Boolean.parseBoolean(activeParameter)
			);
		}

		boolean updated =
				categoryService.updateCategory(category);

		if (updated) {
			response.sendRedirect(
					request.getContextPath()
							+ "/category?action=list"
			);
		} else {
			response.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					"Category could not be updated"
			);
		}
	}

	private void deleteCategory(
			HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {

		Long categoryId = parseLongParameter(
				request,
				"categoryId"
		);

		if (categoryId == null) {
			sendBadRequest(response, "Valid categoryId is required");
			return;
		}

		boolean deleted =
				categoryService.deleteCategory(categoryId);

		if (deleted) {
			response.sendRedirect(
					request.getContextPath()
							+ "/category?action=list"
			);
		} else {
			response.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					"Category could not be deleted"
			);
		}
	}

	private Long parseLongParameter(
			HttpServletRequest request,
			String parameterName) {

		String value = request.getParameter(parameterName);

		if (value == null || value.isBlank()) {
			return null;
		}

		try {
			return Long.parseLong(value.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void sendBadRequest(
			HttpServletResponse response,
			String message)
			throws IOException {

		response.sendError(
				HttpServletResponse.SC_BAD_REQUEST,
				message
		);
	}

	private void handleException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e)
			throws ServletException, IOException {

		log("Error while processing CategoryServlet request", e);

		response.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				"An unexpected error occurred"
		);
	}
}