package ch.ffhs.dameapi.service;


import ch.ffhs.dameapi.model.Color;
import ch.ffhs.dameapi.model.Field;
import ch.ffhs.dameapi.model.Stone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StoneService {

    @Autowired
    private BoardService boardService;


    /**
     * Depending on the field count, will this method creates the stones
     * on the field.
     *
     *
     * --> Error Handling by general Exception Handling
     */
    public void createStones() {

        //default there are 8
        int rowFieldCount = boardService.getBoard().getFieldMatrix().length;

        //define the two empty rows between the players
        int excludedStartRange = rowFieldCount * rowFieldCount / 2 - rowFieldCount;
        int excludedEndRange = rowFieldCount * rowFieldCount / 2 + rowFieldCount -1;

        //put a stone on every brown field on the board, if the stone is outside of excluded range
        for(int y = 0; y < rowFieldCount; y++) {
            for(int x = 0; x < rowFieldCount; x++){

                Field tempField = boardService.getBoard().getSingleFieldFromMatrix(y,x);

                if(tempField.getFieldColor().equals(Color.BROWN)) {
                    if(tempField.getFieldNumber() < excludedStartRange) {
                        tempField.setStone(new Stone(getStoneId(), Color.BLACK));
                        this.addStoneToArray(tempField.getStone());
                    }
                    else if (tempField.getFieldNumber() > excludedEndRange){
                        tempField.setStone(new Stone(getStoneId(), Color.WHITE));
                        this.addStoneToArray(tempField.getStone());
                    }
                }
            }
        }
    }

    public int getStoneId(){
        return boardService.getBoard().getStoneArrayList().size();
    }

    public void addStoneToArray(Stone stone){
        boardService.getBoard().addStone(stone);
    }
}
