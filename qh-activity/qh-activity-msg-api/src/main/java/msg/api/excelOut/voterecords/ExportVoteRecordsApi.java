package msg.api.excelOut.voterecords;


import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 *
 */
public interface ExportVoteRecordsApi {

    /**
     *
     */
    @ExportVoteRecordsListener
    @EventListener
    @Async
    void handle(ExportVoteRecordsEvent exportVoteRecordsEvent);

}
