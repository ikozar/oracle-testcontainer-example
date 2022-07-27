package extb.gba.instrument;

import extb.gba.DBTestConfiguration;
import extb.gba.testcontainer.TestcontainersInitializer;
import extb.gba.instrument.model.InstrumentDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = InstrumentDefinitionRepository.class)
@Import(DBTestConfiguration.class)
//@ContextConfiguration(initializers = TestcontainersInitializer.class)
//@ContextConfiguration(initializers = TestcontainersSubInitializer.class)
@Transactional
@Rollback
class InstrumentDefinitionRepositoryTest {
	private static final String RIC_1 = "ric1";
	private static final String ISIN_1 = "isin1";
	private static final String RIC_2 = "ric2";
	private static final String ISIN_2 = "isin2";

	@Autowired
	InstrumentDefinitionRepository instrumentDefinitionRepository;

	@Test
	void addInstrument() {
		InstrumentDefinition createdInstrument = new InstrumentDefinition(100L, RIC_1, ISIN_1);
		instrumentDefinitionRepository.save(createdInstrument);

		final Optional<InstrumentDefinition> loadedInstrument = instrumentDefinitionRepository.findById(createdInstrument.getInstrumentId());
		assertTrue(loadedInstrument.isPresent());
		compareInstruments(createdInstrument, loadedInstrument.get());
	}

	@Test
	void updateInstrument() {
		InstrumentDefinition createdInstrument = new InstrumentDefinition(100L, RIC_1, ISIN_1);
		instrumentDefinitionRepository.save(createdInstrument);
		Optional<InstrumentDefinition> loadedInstrument = instrumentDefinitionRepository.findById(createdInstrument.getInstrumentId());
		loadedInstrument.get().setIsin(ISIN_2);
		instrumentDefinitionRepository.save(loadedInstrument.get());

		loadedInstrument = instrumentDefinitionRepository.findById(createdInstrument.getInstrumentId());
		assertTrue(loadedInstrument.isPresent());
		assertEquals(ISIN_2, loadedInstrument.get().getIsin());

	}

	@Test
	void findInstruments() {
		long securityId = 100;
		Stream.of(
				new InstrumentDefinition(securityId++, RIC_1, ISIN_1),
				new InstrumentDefinition(securityId++, RIC_2, ISIN_2)
		).forEach(instrumentDefinitionRepository::save);

		List<InstrumentDefinition> instruments = instrumentDefinitionRepository.findByAllKeys(RIC_1, ISIN_2).collect(Collectors.toUnmodifiableList());
		assertEquals(2, instruments.size());
	}

	private void compareInstruments(InstrumentDefinition expected, InstrumentDefinition actual) {
		assertEquals(expected.getInstrumentId(), actual.getInstrumentId());
		assertEquals(expected.getRic(), actual.getRic());
		assertEquals(expected.getIsin(), actual.getIsin());
	}
}
