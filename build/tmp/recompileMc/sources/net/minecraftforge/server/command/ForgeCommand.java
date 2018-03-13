/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.server.command;

import net.minecraft.command.ICommand;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class ForgeCommand extends CommandTreeBase
{
    public ForgeCommand()
    {
        super.addSubcommand(new CommandTps());
        super.addSubcommand(new CommandTrack());
        super.addSubcommand(new CommandGenerate());
        super.addSubcommand(new CommandEntity());
        super.addSubcommand(new CommandTreeHelp(this));
    }

    /**
     * Gets the name of the command
     */
    @Override
    public String getName()
    {
        return "forge";
    }

    @Override
    public void addSubcommand(ICommand command)
    {
        throw new UnsupportedOperationException("Don't add sub-commands to /forge, create your own command.");
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    /**
     * Check if the given ICommandSender has permission to execute this command
     */
    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

    /**
     * Gets the usage string for the command.
     */
    @Override
    public String getUsage(ICommandSender icommandsender)
    {
        return "commands.forge.usage";
    }
}