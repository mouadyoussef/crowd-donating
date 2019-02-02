package org.mql.crowddonating.dao;

import org.mql.crowddonating.models.Association;
import org.mql.crowddonating.models.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationRepository extends JpaRepository<Association, Long>{

}
