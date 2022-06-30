package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Project;
@Repository
public interface ProjectRepo extends CrudRepository<Project, String>{

}
