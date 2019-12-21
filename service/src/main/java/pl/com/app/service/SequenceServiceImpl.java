package pl.com.app.service;


import pl.com.app.exceptions.MyException;
import pl.com.app.repository.SequenceRepository;
import pl.com.app.repository.SequenceRepositoryImpl;

public class SequenceServiceImpl implements SequenceService {

    private SequenceRepository sequenceRepository = new SequenceRepositoryImpl();

    @Override
    public void deleteAll() {
        try {
            sequenceRepository.deleteAll();

        } catch (Exception e) {
            throw new MyException("SQLITE SEQUENCE SERVICE, DELETE ALL");
        }
    }
}
