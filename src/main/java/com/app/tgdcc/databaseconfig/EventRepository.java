package com.app.tgdcc.databaseconfig;

import com.app.tgdcc.dccutils.DccEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface EventRepository extends JpaRepository<DccEvent,Integer>, CrudRepository<DccEvent,Integer> {

    boolean existsByEventId(Integer eventID);
}
