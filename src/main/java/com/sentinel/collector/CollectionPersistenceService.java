package com.sentinel.collector;

import com.sentinel.account.AccountEntity;
import com.sentinel.account.AccountService;
import com.sentinel.collector.model.CollectedAccount;
import com.sentinel.collector.model.CollectedVideo;
import com.sentinel.video.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollectionPersistenceService {

    private final AccountService accountService;
    private final VideoService videoService;

    public void persist(CollectionResult result) {

        for (CollectedAccount collectedAccount : result.getAccounts()) {

            AccountEntity account =
                    accountService.createOrUpdate(collectedAccount);

            result.getVideos()
                    .stream()
                    .filter(video ->
                            collectedAccount.getExternalAccountId()
                                    .equals(video.getExternalAccountId()))
                    .forEach(video ->
                            videoService.createOrUpdate(video, account));
        }
    }
}