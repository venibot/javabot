package commands.economy;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.database.Business;

@DiscordCommand(name = "business", description = "Управление своим бизнесом", aliases = {"biz", "бизнес", "биз"},
        usage = "<Под-команда> [Аргументы]", group = "Экономика", arguments = 3)
public class BusinessCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length == 0) {
            String prefix = context.getDatabaseGuild().getPrefix();
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите одну из доступных под-команд:\n" + prefix + "бизнес помощь\n" + prefix + "бизнес создать <Тип бизнеса>");
            context.sendMessage(errorEmbed).queue();
            return;
        }
        if (arguments[0].equals("помощь") || arguments[0].equals("хелп") || arguments[0].equals("help")) {
            BasicEmbed infoEmbed = new BasicEmbed("info");
            infoEmbed.setTitle("Помощь по бизнесу");
            String prefix = context.getDatabaseGuild().getPrefix();
            infoEmbed.setDescription("**Бизнес** - Пассивный/Активный доход игрока, зависящий от выбранного типа. Существует два типа бизнесов, пассивные или активные. \n  **Пассивные** - В них отключена команда " + prefix + "work, а владелец получает доход раз в 24 часа. \n  **Ручной** - В них участникам компании необходимо прописывать " + prefix + "work для заработка, " + prefix + "work имеет кулдаун в полтора часа (90 минут).\n" +
                    "\n" +
                    "**Банкротство** - Принудительное закрытие бизнеса, вызванное несколькими причинами. Если бизнес активный, то банкротство настанет если не прописывать " + prefix + "work определенное время, которое зависит от бизнеса. Если бизнес пассивный, то он может обанкротиться с определенным шансом каждую третью выплату дохода (3 дня) при условии, что доход был меньше 60% от максимального. Если на балансе компании есть деньги, то вместо банкротства она может выплатить 90% от максимально возможного баланса в течении 1 дня, чтобы избежать закрытия. Во время ожидания оплаты нельзя использовать любые команды компании кроме оплаты или увольнения из компании (Для работников).\n" +
                    "\n" +
                    "**Работники** - Люди, находящиеся в компании в качестве работников. Могут прописывать " + prefix + "work если бизнес активный, если он пассивный, то они прибавляют 2% за одного работника к заработку в день. При прописывании " + prefix + "work работником, он получает только 30% от заработка от этого " + prefix + "work, остальные деньги идут на счет владельца. Владелец может изменить в настройках компании размер выплачиваемого процента, но минимальным является 5%.\n" +
                    "\n" +
                    "**Баланс компании** - Специальный счет бизнеса, откуда оплачиваются все прокачки и откуда снимаются деньги, за избежание банкротства. Он пополняется владельцем компании. Баланс не может быть равен нулю, поэтому при создании компании помимо цены списываются дополнительные 5" + context.getDatabaseGuild().getCurrency() + ", которые переводятся на счет компании.\n" +
                    "\n" +
                    "**Прокачка бизнеса** - Улучшения бизнеса, которые покупаются за счет компании. Каждый бизнес может прокачать максимум лишь 3 навыка.");
            infoEmbed.setFooter("За идею и текст спасибо Не пропадающий#2943");
            context.sendMessage(infoEmbed).queue();
        } else if (arguments[0].equals("создать") || arguments[0].equals("create")) {
            Business userBusiness =
                    context.getDatabase().getUserBusiness(context.getGuild().getIdLong(), context.getAuthor().getIdLong());
            if (userBusiness != null) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "У вас уже есть бизнес " + userBusiness.getType());
                context.sendMessage(errorEmbed).queue();
                return;
            }
            if (arguments.length < 2) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите тип бизнеса");
                context.sendMessage(errorEmbed).queue();
                return;
            }
            String businessType = arguments.length == 2 ? arguments[1] : arguments[1] + arguments[2];

        } else {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Такой под-команды не существует");
            context.sendMessage(errorEmbed).queue();
        }
    }
}
