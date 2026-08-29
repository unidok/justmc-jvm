//import justmc.NumberPrimitive;
//import justmc.Scoreboard;
//import justmc.Text;
//import justmc.Variable;
//import justmc.annotation.EventHandler;
//import justmc.entity.Player;
//import justmc.event.player.PlayerJoinEvent;
//import justmc.event.player.PlayerRightClickEvent;
//
//public final class Scoreboards {
//    private static void updateScoreboard(Scoreboard scoreboard, NumberPrimitive score) {
//        scoreboard.getLine(Text.plain("score")).setText(Text.legacy("Очки: &e").append(score.asText()));
//    }
//
//    @EventHandler
//    private static void onJoin(PlayerJoinEvent event) {
//        Scoreboard scoreboard = new Scoreboard(event.getPlayer().getName(), Text.plain("Скорборд"));
//        updateScoreboard(scoreboard, NumberPrimitive.of(0));
//    }
//
//    @EventHandler
//    private static void onRightClick(PlayerRightClickEvent event) {
//        if (event.getItem().getType() == Text.plain("diamond")) {
//            Player player = event.getPlayer();
//            Text playerName = player.getName();
//            NumberPrimitive newScore = Variable.save(playerName.plus(Text.plain("_score"))).increment();
//            updateScoreboard(Scoreboard.get(playerName), newScore);
//            player.sendMessage(Text.plain("+очко"));
//        }
//    }
//}