package com.glaf.base.modules.image.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.glaf.base.modules.image.domain.Image;
import com.glaf.base.modules.image.mapper.ImageMapper;
import com.glaf.base.modules.image.query.ImageQuery;
import com.glaf.core.base.DataFile;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.IOUtils;

@Service("imageService")
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ImageMapper imageMapper;

	protected IBlobService blobService;

	public ImageServiceImpl() {

	}

	public int count(ImageQuery query) {
		return imageMapper.getImageCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			imageMapper.deleteImageById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				imageMapper.deleteImageById(id);
			}
		}
	}

	public Image getImage(Long id) {
		if (id == null) {
			return null;
		}
		Image image = imageMapper.getImageById(id);
		if (image != null) {
			String fileId = image.getImagePath();
			InputStream inputStream = null;
			ByteArrayOutputStream outputStream = null;
			try {
				DataFile file = blobService.getBlobByFileId(fileId);
				if (file != null) {
					byte[] data = blobService.getBytesByFileId(fileId);
					image.setData(data);
					image.setDataFile(file);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				IOUtils.closeStream(inputStream);
				IOUtils.closeStream(outputStream);
			}
		}
		return image;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getImageCountByQueryCriteria(ImageQuery query) {
		return imageMapper.getImageCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<Image> getImagesByQueryCriteria(int start, int pageSize,
			ImageQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Image> rows = sqlSessionTemplate.selectList("getImages", query,
				rowBounds);
		return rows;
	}

	public List<Image> list(ImageQuery query) {
		List<Image> list = imageMapper.getImages(query);
		return list;
	}

	@Transactional
	public void save(Image image) {
		if (StringUtils.isEmpty(image.getType())) {
			image.setType("user");
		}
		if (image.getId() == null) {
			image.setId(idGenerator.nextId("IMAGE"));
			image.setCreateDate(new Date());
			image.setDeleteFlag(0);

			if (image.getDataFile() != null) {
				String filename = "/mx/image/lob/" + image.getId();
				image.setImagePath(filename);
				image.setFilename(image.getDataFile().getFilename());
			}

			imageMapper.insertImage(image);
		} else {
			if (image.getDataFile() != null) {
				String filename = "/mx/image/lob/" + image.getId();
				image.setImagePath(filename);
				image.setFilename(image.getDataFile().getFilename());
			}
			image.setUpdateDate(new Date());
			imageMapper.updateImage(image);
		}

		if (image.getDataFile() != null) {
			DataFile file = image.getDataFile();
			String path = image.getImagePath();
			file.setStatus(9);
			file.setLastModified(System.currentTimeMillis());
			file.setCreateDate(new Date());
			file.setPath(path);
			file.setFileId("image_" + image.getType() + image.getId());
			file.setName(file.getFilename());
			file.setBusinessKey("image_" + image.getId());
			file.setType("image");
			blobService.insertBlob(file);
		}
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setImageMapper(ImageMapper imageMapper) {
		this.imageMapper = imageMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
