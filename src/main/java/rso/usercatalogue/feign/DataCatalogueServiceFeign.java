package rso.usercatalogue.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import rso.usercatalogue.dto.SummonerDto;

@FeignClient(name = "data-catalogue")
public interface DataCatalogueServiceFeign
{
    @GetMapping("summoner/{username}")
    SummonerDto getSummonerByUsername(@PathVariable("username") String username);

    @GetMapping("/matches/update/{accountId}")
    void updateMatches(@PathVariable("accountId") String accountId);
}
