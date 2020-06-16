package ru.philit.ufs.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.philit.ufs.config.property.ProjectProperties;
import ru.philit.ufs.web.dto.ProjectDto;
import ru.philit.ufs.web.view.GetProjectInfoResp;

/**
 * REST-сервис для возврата версии и состояния сервиса эмуляции систем Банка.
 */
@RestController
@RequestMapping("/")
public class ProjectController {

  private final ProjectProperties project;

  @Autowired
  public ProjectController(ProjectProperties project) {
    this.project = project;
  }

  /**
   * Получение информации о проекте.
   *
   * @return детали о проекте: название и версия
   */
  @RequestMapping(value = "/info", method = RequestMethod.GET)
  public GetProjectInfoResp getProjectInfo() {
    ProjectDto dto = new ProjectDto(project.getName(), project.getVersion());
    return new GetProjectInfoResp().withSuccess(dto);
  }
}
