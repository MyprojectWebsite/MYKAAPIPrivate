package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.Category;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Video;
import org.kaapi.app.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService {

	private Connection con;
	@Autowired
	private DataSource dataSource;

	public CategoryServiceImpl() throws SQLException {

		// /System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());

	}

	@Override
	public List<Category> listCategory(Pagination pagination, String keyword) {

		List<Category> category = new ArrayList<Category>();
		Category dto = null;
		ResultSet rs = null;
		System.err.println(keyword);

		try {

			String sql = "SELECT C.*, MC.maincategoryname, COUNT(CV.categoryid) COUNTVIDEOS FROM TBLCATEGORY C LEFT JOIN TBLCATEGORYVIDEO CV ON C.categoryid=CV.categoryid LEFT JOIN TBLMAINCATEGORY MC ON MC.maincategoryid=C.maincategoryid WHERE C.categoryname LIKE ? GROUP BY C.categoryid, MC.maincategoryname ORDER BY categoryid DESC LIMIT ? OFFSET ? ";
			con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%" + keyword + "%");
			ps.setInt(2, pagination.getPerPage());
			ps.setInt(3, pagination.offset());

			/*
			 * ps.setInt(2, 10); ps.setInt(3, 1);
			 */

			rs = ps.executeQuery();
			/*
			 * rs.next(); System.err.println(rs.getInt(1));
			 */
			while (rs.next()) {
				dto = new Category();
				dto.setCategoryId(rs.getInt("categoryid"));
				dto.setCategoryName(rs.getString("categoryname"));
				dto.setMainCategoryName(rs.getString("maincategoryname"));
				dto.setCategoryLogoUrl(rs.getString("categorylogourl"));
				dto.setCountVideos(rs.getInt("countvideos"));
				category.add(dto);
			}
			return category;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Category getCategory(int categoryid) {

		try {
			ResultSet rs = null;
			Category dto = null;
			System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());
			String sql = "SELECT C.*, MC.maincategoryname, COUNT(CV.categoryid) COUNTVIDEOS FROM TBLCATEGORY C LEFT JOIN TBLCATEGORYVIDEO CV ON C.categoryid=CV.categoryid INNER JOIN TBLMAINCATEGORY MC ON C.maincategoryid=MC.maincategoryid WHERE C.categoryid="
					+ categoryid + " GROUP BY C.categoryid, MC.maincategoryname";
			con = dataSource.getConnection();
			System.out.println(con.getMetaData().getDatabaseProductName() + "Elit Is gay");
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dto = new Category();
				dto.setCategoryId(categoryid);
				dto.setCategoryName(rs.getString("categoryname"));
				dto.setCategoryLogoUrl(rs.getString("categorylogourl"));
				dto.setMainCategoryId(rs.getInt("maincategoryid"));
				dto.setMainCategoryName(rs.getString("maincategoryname"));
				dto.setCountVideos(rs.getInt("countvideos"));
			}
			return dto;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean insertCategory(Category dto) {
		try {
			String sql = "INSERT INTO TBLCATEGORY VALUES(NEXTVAL('seq_category'), ?, ?, ?)";
			con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getCategoryName());
			ps.setString(2, dto.getCategoryLogoUrl());
			ps.setInt(3, dto.getMainCategoryId());
			if (ps.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean updateCategory(Category dto) {
		try {
			String sql = "UPDATE TBLCATEGORY SET categoryname=?, categorylogourl=?, maincategoryid=? WHERE categoryid=?";
			con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getCategoryName());
			ps.setString(2, dto.getCategoryLogoUrl());
			ps.setInt(3, dto.getMainCategoryId());
			ps.setInt(4, dto.getCategoryId());
			if (ps.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean deleteCategory(int categoryid) {
		try {
			String sql = "DELETE FROM TBLCATEGORY WHERE categoryid= ?";
			con = dataSource.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, categoryid);
			if (stmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public int countCategory() {
		try {
			ResultSet rs = null;
			String sql = "SELECT COUNT(CATEGORYID) FROM TBLCATEGORY";
			con = dataSource.getConnection();
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int countVideoByCategory(int categoryid) {
		try {
			ResultSet rs = null;
			String sql = "SELECT COUNT(CV.categoryid) COUNTVIDEOS FROM TBLCATEGORY C LEFT JOIN TBLCATEGORYVIDEO CV ON C.categoryid=CV.categoryid WHERE C.categoryid="
					+ categoryid + " GROUP BY C.categoryid";
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public List<Video> listVideosInCategory(int categoryid, int page, int maxview) {
		ResultSet rs = null;
		Video dto = null;
		List<Video> videos = new ArrayList<Video>();
		System.err.println(categoryid);
		try {
			String sql = "select v.*, u.username, cat.categoryname from tblcategoryvideo cv join tblvideo v on v.videoid = cv.videoid join tbluser u on v.userid = u.userid join tblcategory cat on cat.categoryid = cv.categoryid where cv.categoryid = ? order by v.videoid DESC OFFSET ? LIMIT ?;";
			con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, categoryid);
			ps.setInt(2, (page - 1) * maxview);
			ps.setInt(3, maxview);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new Video();
				dto.setVideoId(rs.getInt("videoid"));
				dto.setVideoName(rs.getString("videoname"));
				dto.setDescription(rs.getString("description"));
				dto.setYoutubeUrl(rs.getString("youtubeurl"));
				dto.setUserId(rs.getInt("userid"));
				dto.setUsername(rs.getString("username"));
				dto.setViewCounts(rs.getInt("viewcount"));
				dto.setPostDate(rs.getDate("postdate"));
				dto.setCategoryName(rs.getString("categoryname"));
				dto.setPublicView(rs.getBoolean("publicview"));
				videos.add(dto);
			}

			return videos;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(rs);
		return null;
	}

}
