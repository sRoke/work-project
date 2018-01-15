package msg.api.excelOut.voteworks;


import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 *
 */
public interface ExportVoteWorksApi {

    /**
     *
     */
    @ExportVoteWorksListener
    @EventListener
    @Async
    void handle(ExportVoteWorksEvent exportVoteWorksEvent);

}
