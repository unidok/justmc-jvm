package justmc;

import justmc.annotation.Inline;
import justmc.enums.ScoreboardFormatting;

@Inline
public final class Scoreboard extends Primitive {
    public final Text id;

    private Scoreboard(Text id) {
        this.id = id;
    }

    public Scoreboard(Text id, Text title) {
        this(id);
        Unsafe.operation("game_create_scoreboard", MapPrimitive.of(
                Pair.of("id", id),
                Pair.of("display_name", title)
        ));
    }

    @Inline
    public final class Line extends Primitive {
        public final Text lineId;

        private Line(Text lineId) {
            this.lineId = lineId;
        }

        public void setText(Text text) {
            Unsafe.operation("game_set_scoreboard_line_display", MapPrimitive.of(
                    Pair.of("id", id),
                    Pair.of("line", lineId),
                    Pair.of("display", text)
            ));
        }

        public void setText(Text text, int score) {
            setText(text, score, null, null);
        }

        public void setText(Text text, int score, Text format, ScoreboardFormatting formatting) {
            Unsafe.operation("game_set_scoreboard_line", MapPrimitive.of(
                    Pair.of("id", id),
                    Pair.of("line", lineId),
                    Pair.of("display", text),
                    Pair.of("score", NumberPrimitive.of(score)),
                    Pair.of("format_content", format),
                    Pair.of("format", formatting)
            ));
        }

        public void setFormatting(Text format, ScoreboardFormatting formatting) {
            Unsafe.operation("game_set_scoreboard_line_format", MapPrimitive.of(
                    Pair.of("id", id),
                    Pair.of("line", lineId),
                    Pair.of("format_content", format),
                    Pair.of("format", formatting)
            ));
        }

        public void setScore(int score) {
            Unsafe.operation("game_set_scoreboard_score", MapPrimitive.of(
                    Pair.of("id", id),
                    Pair.of("text", lineId),
                    Pair.of("score", NumberPrimitive.of(score))
            ));
        }

        public void remove() {
            Unsafe.operation("game_remove_scoreboard_score_by_name", MapPrimitive.of(
                    Pair.of("id", id),
                    Pair.of("text", lineId)
            ));
        }
    }

    public static Scoreboard get(Text id) {
        return new Scoreboard(id);
    }

    public void setTitle(Text title) {
        Unsafe.operation("game_set_scoreboard_title", MapPrimitive.of(
                Pair.of("id", id),
                Pair.of("title", title)
        ));
    }

    public void setFormatting(ScoreboardFormatting formatting, Text format) {
        Unsafe.operation("game_set_scoreboard_number_format", MapPrimitive.of(
                Pair.of("id", id),
                Pair.of("format_content", format),
                Pair.of("format", formatting)
        ));
    }

    public Line getLine(Text lineId) {
        return new Line(lineId);
    }

    public void removeLinesByScore(int score) {
        Unsafe.operation("game_remove_scoreboard_score_by_score", MapPrimitive.of(
                Pair.of("id", id),
                Pair.of("score", NumberPrimitive.of(score))
        ));
    }

    public void clear() {
        Unsafe.operation("game_clear_scoreboard_scores", MapPrimitive.of(
                Pair.of("id", id)
        ));
    }

    public void remove() {
        Unsafe.operation("game_remove_scoreboard", MapPrimitive.of(
                Pair.of("id", id)
        ));
    }
}