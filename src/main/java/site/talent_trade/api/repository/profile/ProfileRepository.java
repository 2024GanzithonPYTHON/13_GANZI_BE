package site.talent_trade.api.repository.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import site.talent_trade.api.domain.profile.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
