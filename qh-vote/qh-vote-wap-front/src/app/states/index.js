import conf                                                     from "../conf";
import sMain                                                    from "./main";
import sMainOtherMain                                           from "./main/otherMain";
import sMainBindPhone                                           from "./main/bindPhone";
import sMainWxLogin                                             from "./main/wxLogin";
import sMainVoteApp                                            from "./main/voteApp";
import sMainLoginTime                                           from "./main/loginTime";
// import   sMainJoinUs                                         from "./main/joinUS";
import sMainVoteAppHome                                        from "./main/voteApp/home";



//--------------------------------------------------------------no.1   投票活动
import sMainVoteAppVote                                        from "./main/voteApp/vote";
import sMainVoteAppVotHome                                     from "./main/voteApp/vote/votHome";
import sMainVoteAppVotSignUp                                   from "./main/voteApp/vote/votSignUp";
import sMainVoteAppMyVote                                      from "./main/voteApp/vote/myVote";

export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainOtherMain.name,
    sMainBindPhone.name,
    sMainWxLogin.name,
    sMainVoteApp.name,
    sMainLoginTime.name,
    sMainVoteAppHome.name,
    sMainVoteAppVote.name,
    sMainVoteAppVotHome.name,
    sMainVoteAppVotSignUp.name,
    sMainVoteAppMyVote.name,


])