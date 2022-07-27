package extb.gba.instrument;

import extb.gba.instrument.model.InstrumentDefinition;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface InstrumentDefinitionRepository extends CrudRepository<InstrumentDefinition, Long> {
    @Query("select * from instrument_definition where ric = :ric or isin = :isin")
    Stream<InstrumentDefinition> findByAllKeys(@Param(value = "ric") String ric, @Param(value = "isin") String isin);
}
