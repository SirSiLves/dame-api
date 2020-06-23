package ch.ffhs.dameapi.controller;


import ch.ffhs.dameapi.model.Field;
import ch.ffhs.dameapi.model.Validity;
import ch.ffhs.dameapi.service.BoardService;
import ch.ffhs.dameapi.service.MoveService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


class ClickedFields {

    int currentFieldNumber, targetFieldNumber;

    /**
     * set the current field number
     *
     *
     * @param currentField of type Field
     *                     --> Error Handling by general Exception Handling
     */
    @JsonProperty("currentField")
    private void setCurrentFieldNumber(@Valid Map<String, Object> currentField) {
        currentFieldNumber = Integer.parseInt(currentField.get("fieldNumber").toString());
    }

    /**
     * set the target field number
     *
     *
     * @param targetField of type Field
     * --> Error Handling by general Exception Handling
     */
    @JsonProperty("targetField")
    private void setTargetFieldNumber(@Valid Map<String, Object> targetField) {
        targetFieldNumber = Integer.parseInt(targetField.get("fieldNumber").toString());
    }
}

@RestController
@RequestMapping("/api/move")
public class MoveController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MoveService moveService;


    /**
     * Validates a move and do it if it's valid (simple or hop move).
     * Post Method on /api/move/default
     *
     * Request Method: POST
     *
     * @param clickedFields object of class @see ClickedFields
     * @return validation of type @see Validity & HTTP Status
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "default", method = RequestMethod.POST)
    public ResponseEntity<Validity> defaultMove(@Valid @RequestBody ClickedFields clickedFields) {

        Validity validation = null;

        //get fields from right http session
        Field currentField = boardService.getField(clickedFields.currentFieldNumber);
        Field targetField = boardService.getField(clickedFields.targetFieldNumber);

        //check if simple move or hop move
        if (currentField != null && targetField != null) {
            if (Math.abs(currentField.getFieldNumber() - targetField.getFieldNumber()) < 12) {
                validation = moveService.doSimpleMove(currentField, targetField);
            } else {
                validation = moveService.doHopMove(currentField, targetField);
            }
        }

        return new ResponseEntity<>(validation, HttpStatus.OK);
    }

    /**
     * Run botMove if its enabled. Post Method on /api/move/doBotMove
     *
     * Request Method: POST
     * no params are required
     *
     * @return validation of type @see Validity & HTTP Status
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "doBotMove", method = RequestMethod.POST)
    public ResponseEntity<Validity> doBotMove() {
        Validity validation = moveService.doBotMove();

        return new ResponseEntity<>(validation, HttpStatus.OK);
    }

    /**
     * Revert last move. Get Method on /api/move/loadPreviousGame
     *
     * Request Method: GET
     * no params are required
     *
     * @return String with message & HTTP Status
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "loadPreviousMove", method = RequestMethod.POST)
    public ResponseEntity<String> loadPreviousMove() {
        if (boardService.getBoard().getMoveHistory().size() > 0) {
            boardService.loadPreviousMove();
            return new ResponseEntity<>("Last move has been reverted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There are no more moves available to revert.", HttpStatus.OK);
        }
    }
}

