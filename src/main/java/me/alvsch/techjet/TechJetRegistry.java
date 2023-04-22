package me.alvsch.techjet;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.alvsch.techjet.enums.VanishType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TechJetRegistry {

    private boolean globalmute = false;

    private final List<UUID> silentContainer = new ArrayList<>();
    private final List<UUID> freeze = new ArrayList<>();
    private final HashMap<Player, VanishType> vanish = new HashMap<>();
    private final HashMap<Player, ItemStack[]> modmode = new HashMap<>();


}
