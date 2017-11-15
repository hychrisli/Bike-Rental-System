package cmpe282.station.controller;

import static cmpe282.station.config.UrlConstants.PROJECT;
import static cmpe282.station.config.JsonConstants.KEY_PROJECT;
import static cmpe282.station.config.JsonConstants.KEY_PROJECTS;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cmpe282.station.cassandra.entity.Project;
import cmpe282.station.config.JsonResponse;
import cmpe282.station.error.AppException;
import cmpe282.station.service.ProjectService;

@RestController
public class ProjectController extends AbstractController {

	@Autowired
	ProjectService projectSvc;

	@GetMapping(PROJECT)
	public ResponseEntity<JsonResponse> getProjects() {
		List<Project> projects = projectSvc.getAllProjects();
		if (projects.isEmpty())
			return notFound();
		return success(KEY_PROJECTS, projects);
	}

	@GetMapping(PROJECT + "/{id}")
	public ResponseEntity<JsonResponse> getProject(@PathVariable Long id) {
		Project project = projectSvc.findProject(id);
		if (project != null)
			return success(KEY_PROJECT, project);
		return notFound();
	}

	@PostMapping(PROJECT)
	public ResponseEntity<JsonResponse> createProject(@RequestBody Project project) {
		if (projectSvc.createProject(project))
			return created(PROJECT + "/" + project.getId());
		return conflict();
	}

	@PutMapping(PROJECT + "/{id}")
	public ResponseEntity<JsonResponse> updateProject(@PathVariable Long id, HttpServletRequest req)
			throws AppException {
		Project project = projectSvc.updateProject(id, req);
		if (project != null)
			return success(KEY_PROJECT, project);
		return notFound();
	}

	@DeleteMapping(PROJECT + "/{id}")
	public ResponseEntity<JsonResponse> deleteProject(@PathVariable Long id) {
		Project project = projectSvc.deleteProject(id);
		if (project != null) {
			return success(KEY_PROJECT, project);
		}
		return notFound();
	}
}
