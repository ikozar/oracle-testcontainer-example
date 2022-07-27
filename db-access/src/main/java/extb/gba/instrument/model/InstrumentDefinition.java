package extb.gba.instrument.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.Objects;

// Instrument DTO, TODO consider to move in separate module/artifact
public class InstrumentDefinition implements Persistable<Long> {
    @Id
    private Long instrumentId;
    private String ric;
    private String isin;

    @ReadOnlyProperty
    private LocalDateTime createdTs;

    public InstrumentDefinition() {
    }

    public InstrumentDefinition(Long instrumentId, String ric, String isin) {
        this.instrumentId = instrumentId;
        this.ric = ric;
        this.isin = isin;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getRic() {
        return ric;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstrumentDefinition)) return false;
        InstrumentDefinition instrumentDefinition = (InstrumentDefinition) o;
        return Objects.equals(instrumentId, instrumentDefinition.instrumentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrumentId);
    }

    @Override
    public Long getId() {
        return instrumentId;
    }

    @Override
    public boolean isNew() {
        return createdTs == null;
    }
}
