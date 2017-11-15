package cmpe282.station.service.impl;

import static cmpe282.station.error.ErrorCode.ERR_INVALID_JSON;
import static cmpe282.station.error.ErrorCode.ERR_IO_EXCEPTION;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import cmpe282.station.cassandra.entity.Project;
import cmpe282.station.error.AppException;
import cmpe282.station.repository.ProjectRepository;
import cmpe282.station.service.MapIdService;
import cmpe282.station.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository repo;
	
	@Autowired
	private MapIdService mapIdSvc;
	
	@Override
	public List<Project> getAllProjects() {
		return Lists.newArrayList(repo.findAll());
	}

	@Override
	public Project findProject(Long id) {
		return repo.findOne(mapIdSvc.toMapId(id));
	}

	@Override
	public Boolean createProject(Project project) {
		if (findProject(project.getId()) != null)
			return false;
		repo.save(project);
		return true;
	}

	@Override
	public Project updateProject(Long id, HttpServletRequest req) throws AppException {
		Project project = findProject(id);
		
		if (project != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				Project updatedProject = objectMapper
						.readerForUpdating(project)
						.readValue(req.getReader());
				repo.save(updatedProject);
				return updatedProject;
			} catch (JsonProcessingException e) {
				throw new AppException(ERR_INVALID_JSON, e);
			} catch (IOException e) {
				throw new AppException(ERR_IO_EXCEPTION, e);
			}
		}
		return null;
	}

	@Override
	public Project deleteProject(Long id) {
		Project project = findProject(id);
		if (project != null) {
			repo.delete(mapIdSvc.toMapId(id));
			return project;
		}
		return null;
	}

}
