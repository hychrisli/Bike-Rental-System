package cmpe282.station.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cmpe282.station.cassandra.entity.Project;
import cmpe282.station.error.AppException;

public interface ProjectService {
	
	public List<Project> getAllProjects();
	
	public Project findProject (Long id);
	
	public Boolean createProject (Project project);
	
	public Project updateProject (Long id, HttpServletRequest req) throws AppException;
	
	public Project deleteProject (Long id);
}
