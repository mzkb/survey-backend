package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.dao.PublisherDao;
import com.github.mzkb.survey.app.model.Publisher;
import com.github.mzkb.survey.app.service.exception.AlreadyExistsException;
import com.github.mzkb.survey.app.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherServiceImpl.class);

    private final PasswordService passwordService;
    private final PublisherDao publisherDao;

    public PublisherServiceImpl(PasswordService passwordService, PublisherDao publisherDao) {
        this.passwordService = passwordService;
        this.publisherDao = publisherDao;
    }

    @Override
    public Publisher create(Publisher publisher) throws AlreadyExistsException {
        Publisher byEmail = publisherDao.findByEmail(publisher.getEmail());
        if (byEmail != null) {
            throw new AlreadyExistsException();
        }

        // We except raw password when calling this service
        publisher.setPassword(passwordService.hash(publisher.getPassword()));

        return publisherDao.save(publisher);
    }

    @Override
    public Publisher get(UUID uuid) throws NotFoundException {
        return get(uuid.toString());
    }

    @Override
    public Publisher get(String uuid) throws NotFoundException {
        Publisher byUuid = publisherDao.findByUuid(uuid);
        if (byUuid == null) {
            throw new NotFoundException();
        }

        return byUuid;
    }

    @Override
    public void delete(UUID uuid) throws NotFoundException {
        delete(uuid.toString());
    }

    @Override
    public void delete(String uuid) throws NotFoundException {
        Publisher byUuid = publisherDao.findByUuid(uuid.toString());
        if (byUuid == null) {
            throw new NotFoundException();
        }

        publisherDao.delete(byUuid);
    }

    @Override
    public Publisher getByEmail(String email) {
        return publisherDao.findByEmail(email);
    }
}
