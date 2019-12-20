/**
 * 
 *    Copyright 2017 Florian Erhard
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 */
/*
 * Copyright (c) 2002-2015, the original author or authors.
 *
 * This software is distributable under the BSD license. See the terms of the
 * BSD license in the documentation provided with this software.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package jline.internal;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Infocmp helper methods.
 *
 * @author <a href="mailto:gnodet@gmail.com">Guillaume Nodet</a>
 */
public class InfoCmp {

    private static final Map<String, String> CAPS = new HashMap<String, String>();

    public static String getInfoCmp(
            String terminal
    ) throws IOException, InterruptedException {
        String caps = CAPS.get(terminal);
        if (caps == null) {
            Process p = new ProcessBuilder("infocmp", terminal).start();
            caps = TerminalLineSettings.waitAndCapture(p);
            CAPS.put(terminal, caps);
        }
        return caps;
    }

    public static String getAnsiCaps() {
        return ANSI_CAPS;
    }

    public static void parseInfoCmp(
            String capabilities,
            Set<String> bools,
            Map<String, Integer> ints,
            Map<String, String> strings
    ) {
        String[] lines = capabilities.split("\n");
        for (int i = 2; i < lines.length; i++) {
            Matcher m = Pattern.compile("\\s*(([^,]|\\\\,)+)\\s*[,$]").matcher(lines[i]);
            while (m.find()) {
                String cap = m.group(1);
                if (cap.contains("#")) {
                    int index = cap.indexOf('#');
                    String key = cap.substring(0, index);
                    String val = cap.substring(index + 1);
                    int iVal = Integer.valueOf(val);
                    for (String name : getNames(key)) {
                        ints.put(name, iVal);
                    }
                } else if (cap.contains("=")) {
                    int index = cap.indexOf('=');
                    String key = cap.substring(0, index);
                    String val = cap.substring(index + 1);
                    for (String name : getNames(key)) {
                        strings.put(name, val);
                    }
                } else {
                    for (String name : getNames(cap)) {
                        bools.add(name);
                    }
                }
            }
        }
    }

    public static String[] getNames(String name) {
        String[] names = NAMES.get(name);
        return names != null ? names : new String[] { name };
    }

    private static final Map<String, String[]> NAMES;
    static {
        String[][] list = {
                { "auto_left_margin", "bw", "bw" },
                { "auto_right_margin", "am", "am" },
                { "back_color_erase", "bce", "ut" },
                { "can_change", "ccc", "cc" },
                { "ceol_standout_glitch", "xhp", "xs" },
                { "col_addr_glitch", "xhpa", "YA" },
                { "cpi_changes_res", "cpix", "YF" },
                { "cr_cancels_micro_mode", "crxm", "YB" },
                { "dest_tabs_magic_smso", "xt", "xt" },
                { "eat_newline_glitch", "xenl", "xn" },
                { "erase_overstrike", "eo", "eo" },
                { "generic_type", "gn", "gn" },
                { "hard_copy", "hc", "hc" },
                { "hard_cursor", "chts", "HC" },
                { "has_meta_key", "km", "km" },
                { "has_print_wheel", "daisy", "YC" },
                { "has_status_line", "hs", "hs" },
                { "hue_lightness_saturation", "hls", "hl" },
                { "insert_null_glitch", "in", "in" },
                { "lpi_changes_res", "lpix", "YG" },
                { "memory_above", "da", "da" },
                { "memory_below", "db", "db" },
                { "move_insert_mode", "mir", "mi" },
                { "move_standout_mode", "msgr", "ms" },
                { "needs_xon_xoff", "nxon", "nx" },
                { "no_esc_ctlc", "xsb", "xb" },
                { "no_pad_char", "npc", "NP" },
                { "non_dest_scroll_region", "ndscr", "ND" },
                { "non_rev_rmcup", "nrrmc", "NR" },
                { "over_strike", "os", "os" },
                { "prtr_silent", "mc5i", "5i" },
                { "row_addr_glitch", "xvpa", "YD" },
                { "semi_auto_right_margin", "sam", "YE" },
                { "status_line_esc_ok", "eslok", "es" },
                { "tilde_glitch", "hz", "hz" },
                { "transparent_underline", "ul", "ul" },
                { "xon_xoff", "xon", "xo" },
                { "columns", "cols", "co" },
                { "init_tabs", "it", "it" },
                { "label_height", "lh", "lh" },
                { "label_width", "lw", "lw" },
                { "lines", "lines", "li" },
                { "lines_of_memory", "lm", "lm" },
                { "magic_cookie_glitch", "xmc", "sg" },
                { "max_attributes", "ma", "ma" },
                { "max_colors", "colors", "Co" },
                { "max_pairs", "pairs", "pa" },
                { "maximum_windows", "wnum", "MW" },
                { "no_color_video", "ncv", "NC" },
                { "num_labels", "nlab", "Nl" },
                { "padding_baud_rate", "pb", "pb" },
                { "virtual_terminal", "vt", "vt" },
                { "width_status_line", "wsl", "ws" },
                { "bit_image_entwining", "bitwin", "Yo" },
                { "bit_image_type", "bitype", "Yp" },
                { "buffer_capacity", "bufsz", "Ya" },
                { "buttons", "btns", "BT" },
                { "dot_horz_spacing", "spinh", "Yc" },
                { "dot_vert_spacing", "spinv", "Yb" },
                { "max_micro_address", "maddr", "Yd" },
                { "max_micro_jump", "mjump", "Ye" },
                { "micro_col_size", "mcs", "Yf" },
                { "micro_line_size", "mls", "Yg" },
                { "number_of_pins", "npins", "Yh" },
                { "output_res_char", "orc", "Yi" },
                { "output_res_horz_inch", "orhi", "Yk" },
                { "output_res_line", "orl", "Yj" },
                { "output_res_vert_inch", "orvi", "Yl" },
                { "print_rate", "cps", "Ym" },
                { "wide_char_size", "widcs", "Yn" },
                { "acs_chars", "acsc", "ac" },
                { "back_tab", "cbt", "bt" },
                { "bell", "bel", "bl" },
                { "carriage_return", "cr", "cr" },
                { "change_char_pitch", "cpi", "ZA" },
                { "change_line_pitch", "lpi", "ZB" },
                { "change_res_horz", "chr", "ZC" },
                { "change_res_vert", "cvr", "ZD" },
                { "change_scroll_region", "csr", "cs" },
                { "char_padding", "rmp", "rP" },
                { "clear_all_tabs", "tbc", "ct" },
                { "clear_margins", "mgc", "MC" },
                { "clear_screen", "clear", "cl" },
                { "clr_bol", "el1", "cb" },
                { "clr_eol", "el", "ce" },
                { "clr_eos", "ed", "cd" },
                { "column_address", "hpa", "ch" },
                { "command_character", "cmdch", "CC" },
                { "create_window", "cwin", "CW" },
                { "cursor_address", "cup", "cm" },
                { "cursor_down", "cud1", "do" },
                { "cursor_home", "home", "ho" },
                { "cursor_invisible", "civis", "vi" },
                { "cursor_left", "cub1", "le" },
                { "cursor_mem_address", "mrcup", "CM" },
                { "cursor_normal", "cnorm", "ve" },
                { "cursor_right", "cuf1", "nd" },
                { "cursor_to_ll", "ll", "ll" },
                { "cursor_up", "cuu1", "up" },
                { "cursor_visible", "cvvis", "vs" },
                { "define_char", "defc", "ZE" },
                { "delete_character", "dch1", "dc" },
                { "delete_line", "dl1", "dl" },
                { "dial_phone", "dial", "DI" },
                { "dis_status_line", "dsl", "ds" },
                { "display_clock", "dclk", "DK" },
                { "down_half_line", "hd", "hd" },
                { "ena_acs", "enacs", "eA" },
                { "enter_alt_charset_mode", "smacs", "as" },
                { "enter_am_mode", "smam", "SA" },
                { "enter_blink_mode", "blink", "mb" },
                { "enter_bold_mode", "bold", "md" },
                { "enter_ca_mode", "smcup", "ti" },
                { "enter_delete_mode", "smdc", "dm" },
                { "enter_dim_mode", "dim", "mh" },
                { "enter_doublewide_mode", "swidm", "ZF" },
                { "enter_draft_quality", "sdrfq", "ZG" },
                { "enter_insert_mode", "smir", "im" },
                { "enter_italics_mode", "sitm", "ZH" },
                { "enter_leftward_mode", "slm", "ZI" },
                { "enter_micro_mode", "smicm", "ZJ" },
                { "enter_near_letter_quality", "snlq", "ZK" },
                { "enter_normal_quality", "snrmq", "ZL" },
                { "enter_protected_mode", "prot", "mp" },
                { "enter_reverse_mode", "rev", "mr" },
                { "enter_secure_mode", "invis", "mk" },
                { "enter_shadow_mode", "sshm", "ZM" },
                { "enter_standout_mode", "smso", "so" },
                { "enter_subscript_mode", "ssubm", "ZN" },
                { "enter_superscript_mode", "ssupm", "ZO" },
                { "enter_underline_mode", "smul", "us" },
                { "enter_upward_mode", "sum", "ZP" },
                { "enter_xon_mode", "smxon", "SX" },
                { "erase_chars", "ech", "ec" },
                { "exit_alt_charset_mode", "rmacs", "ae" },
                { "exit_am_mode", "rmam", "RA" },
                { "exit_attribute_mode", "sgr0", "me" },
                { "exit_ca_mode", "rmcup", "te" },
                { "exit_delete_mode", "rmdc", "ed" },
                { "exit_doublewide_mode", "rwidm", "ZQ" },
                { "exit_insert_mode", "rmir", "ei" },
                { "exit_italics_mode", "ritm", "ZR" },
                { "exit_leftward_mode", "rlm", "ZS" },
                { "exit_micro_mode", "rmicm", "ZT" },
                { "exit_shadow_mode", "rshm", "ZU" },
                { "exit_standout_mode", "rmso", "se" },
                { "exit_subscript_mode", "rsubm", "ZV" },
                { "exit_superscript_mode", "rsupm", "ZW" },
                { "exit_underline_mode", "rmul", "ue" },
                { "exit_upward_mode", "rum", "ZX" },
                { "exit_xon_mode", "rmxon", "RX" },
                { "fixed_pause", "pause", "PA" },
                { "flash_hook", "hook", "fh" },
                { "flash_screen", "flash", "vb" },
                { "form_feed", "ff", "ff" },
                { "from_status_line", "fsl", "fs" },
                { "goto_window", "wingo", "WG" },
                { "hangup", "hup", "HU" },
                { "init_1string", "is1", "i1" },
                { "init_2string", "is2", "is" },
                { "init_3string", "is3", "i3" },
                { "init_file", "if", "if" },
                { "init_prog", "iprog", "iP" },
                { "initialize_color", "initc", "Ic" },
                { "initialize_pair", "initp", "Ip" },
                { "insert_character", "ich1", "ic" },
                { "insert_line", "il1", "al" },
                { "insert_padding", "ip", "ip" },
                { "key_a1", "ka1", "K1" },
                { "key_a3", "ka3", "K3" },
                { "key_b2", "kb2", "K2" },
                { "key_backspace", "kbs", "kb" },
                { "key_beg", "kbeg", "@1" },
                { "key_btab", "kcbt", "kB" },
                { "key_c1", "kc1", "K4" },
                { "key_c3", "kc3", "K5" },
                { "key_cancel", "kcan", "@2" },
                { "key_catab", "ktbc", "ka" },
                { "key_clear", "kclr", "kC" },
                { "key_close", "kclo", "@3" },
                { "key_command", "kcmd", "@4" },
                { "key_copy", "kcpy", "@5" },
                { "key_create", "kcrt", "@6" },
                { "key_ctab", "kctab", "kt" },
                { "key_dc", "kdch1", "kD" },
                { "key_dl", "kdl1", "kL" },
                { "key_down", "kcud1", "kd" },
                { "key_eic", "krmir", "kM" },
                { "key_end", "kend", "@7" },
                { "key_enter", "kent", "@8" },
                { "key_eol", "kel", "kE" },
                { "key_eos", "ked", "kS" },
                { "key_exit", "kext", "@9" },
                { "key_f0", "kf0", "k0" },
                { "key_f1", "kf1", "k1" },
                { "key_f10", "kf10", "k;" },
                { "key_f11", "kf11", "F1" },
                { "key_f12", "kf12", "F2" },
                { "key_f13", "kf13", "F3" },
                { "key_f14", "kf14", "F4" },
                { "key_f15", "kf15", "F5" },
                { "key_f16", "kf16", "F6" },
                { "key_f17", "kf17", "F7" },
                { "key_f18", "kf18", "F8" },
                { "key_f19", "kf19", "F9" },
                { "key_f2", "kf2", "k2" },
                { "key_f20", "kf20", "FA" },
                { "key_f21", "kf21", "FB" },
                { "key_f22", "kf22", "FC" },
                { "key_f23", "kf23", "FD" },
                { "key_f24", "kf24", "FE" },
                { "key_f25", "kf25", "FF" },
                { "key_f26", "kf26", "FG" },
                { "key_f27", "kf27", "FH" },
                { "key_f28", "kf28", "FI" },
                { "key_f29", "kf29", "FJ" },
                { "key_f3", "kf3", "k3" },
                { "key_f30", "kf30", "FK" },
                { "key_f31", "kf31", "FL" },
                { "key_f32", "kf32", "FM" },
                { "key_f33", "kf33", "FN" },
                { "key_f34", "kf34", "FO" },
                { "key_f35", "kf35", "FP" },
                { "key_f36", "kf36", "FQ" },
                { "key_f37", "kf37", "FR" },
                { "key_f38", "kf38", "FS" },
                { "key_f39", "kf39", "FT" },
                { "key_f4", "kf4", "k4" },
                { "key_f40", "kf40", "FU" },
                { "key_f41", "kf41", "FV" },
                { "key_f42", "kf42", "FW" },
                { "key_f43", "kf43", "FX" },
                { "key_f44", "kf44", "FY" },
                { "key_f45", "kf45", "FZ" },
                { "key_f46", "kf46", "Fa" },
                { "key_f47", "kf47", "Fb" },
                { "key_f48", "kf48", "Fc" },
                { "key_f49", "kf49", "Fd" },
                { "key_f5", "kf5", "k5" },
                { "key_f50", "kf50", "Fe" },
                { "key_f51", "kf51", "Ff" },
                { "key_f52", "kf52", "Fg" },
                { "key_f53", "kf53", "Fh" },
                { "key_f54", "kf54", "Fi" },
                { "key_f55", "kf55", "Fj" },
                { "key_f56", "kf56", "Fk" },
                { "key_f57", "kf57", "Fl" },
                { "key_f58", "kf58", "Fm" },
                { "key_f59", "kf59", "Fn" },
                { "key_f6", "kf6", "k6" },
                { "key_f60", "kf60", "Fo" },
                { "key_f61", "kf61", "Fp" },
                { "key_f62", "kf62", "Fq" },
                { "key_f63", "kf63", "Fr" },
                { "key_f7", "kf7", "k7" },
                { "key_f8", "kf8", "k8" },
                { "key_f9", "kf9", "k9" },
                { "key_find", "kfnd", "@0" },
                { "key_help", "khlp", "%1" },
                { "key_home", "khome", "kh" },
                { "key_ic", "kich1", "kI" },
                { "key_il", "kil1", "kA" },
                { "key_left", "kcub1", "kl" },
                { "key_ll", "kll", "kH" },
                { "key_mark", "kmrk", "%2" },
                { "key_message", "kmsg", "%3" },
                { "key_move", "kmov", "%4" },
                { "key_next", "knxt", "%5" },
                { "key_npage", "knp", "kN" },
                { "key_open", "kopn", "%6" },
                { "key_options", "kopt", "%7" },
                { "key_ppage", "kpp", "kP" },
                { "key_previous", "kprv", "%8" },
                { "key_print", "kprt", "%9" },
                { "key_redo", "krdo", "%0" },
                { "key_reference", "kref", "&1" },
                { "key_refresh", "krfr", "&2" },
                { "key_replace", "krpl", "&3" },
                { "key_restart", "krst", "&4" },
                { "key_resume", "kres", "&5" },
                { "key_right", "kcuf1", "kr" },
                { "key_save", "ksav", "&6" },
                { "key_sbeg", "kBEG", "&9" },
                { "key_scancel", "kCAN", "&0" },
                { "key_scommand", "kCMD", "*1" },
                { "key_scopy", "kCPY", "*2" },
                { "key_screate", "kCRT", "*3" },
                { "key_sdc", "kDC", "*4" },
                { "key_sdl", "kDL", "*5" },
                { "key_select", "kslt", "*6" },
                { "key_send", "kEND", "*7" },
                { "key_seol", "kEOL", "*8" },
                { "key_sexit", "kEXT", "*9" },
                { "key_sf", "kind", "kF" },
                { "key_sfind", "kFND", "*0" },
                { "key_shelp", "kHLP", "#1" },
                { "key_shome", "kHOM", "#2" },
                { "key_sic", "kIC", "#3" },
                { "key_sleft", "kLFT", "#4" },
                { "key_smessage", "kMSG", "%a" },
                { "key_smove", "kMOV", "%b" },
                { "key_snext", "kNXT", "%c" },
                { "key_soptions", "kOPT", "%d" },
                { "key_sprevious", "kPRV", "%e" },
                { "key_sprint", "kPRT", "%f" },
                { "key_sr", "kri", "kR" },
                { "key_sredo", "kRDO", "%g" },
                { "key_sreplace", "kRPL", "%h" },
                { "key_sright", "kRIT", "%i" },
                { "key_srsume", "kRES", "%j" },
                { "key_ssave", "kSAV", "!1" },
                { "key_ssuspend", "kSPD", "!2" },
                { "key_stab", "khts", "kT" },
                { "key_sundo", "kUND", "!3" },
                { "key_suspend", "kspd", "&7" },
                { "key_undo", "kund", "&8" },
                { "key_up", "kcuu1", "ku" },
                { "keypad_local", "rmkx", "ke" },
                { "keypad_xmit", "smkx", "ks" },
                { "lab_f0", "lf0", "l0" },
                { "lab_f1", "lf1", "l1" },
                { "lab_f10", "lf10", "la" },
                { "lab_f2", "lf2", "l2" },
                { "lab_f3", "lf3", "l3" },
                { "lab_f4", "lf4", "l4" },
                { "lab_f5", "lf5", "l5" },
                { "lab_f6", "lf6", "l6" },
                { "lab_f7", "lf7", "l7" },
                { "lab_f8", "lf8", "l8" },
                { "lab_f9", "lf9", "l9" },
                { "label_format", "fln", "Lf" },
                { "label_off", "rmln", "LF" },
                { "label_on", "smln", "LO" },
                { "meta_off", "rmm", "mo" },
                { "meta_on", "smm", "mm" },
                { "micro_column_address", "mhpa", "ZY" },
                { "micro_down", "mcud1", "ZZ" },
                { "micro_left", "mcub1", "Za" },
                { "micro_right", "mcuf1", "Zb" },
                { "micro_row_address", "mvpa", "Zc" },
                { "micro_up", "mcuu1", "Zd" },
                { "newline", "nel", "nw" },
                { "order_of_pins", "porder", "Ze" },
                { "orig_colors", "oc", "oc" },
                { "orig_pair", "op", "op" },
                { "pad_char", "pad", "pc" },
                { "parm_dch", "dch", "DC" },
                { "parm_delete_line", "dl", "DL" },
                { "parm_down_cursor", "cud", "DO" },
                { "parm_down_micro", "mcud", "Zf" },
                { "parm_ich", "ich", "IC" },
                { "parm_index", "indn", "SF" },
                { "parm_insert_line", "il", "AL" },
                { "parm_left_cursor", "cub", "LE" },
                { "parm_left_micro", "mcub", "Zg" },
                { "parm_right_cursor", "cuf", "RI" },
                { "parm_right_micro", "mcuf", "Zh" },
                { "parm_rindex", "rin", "SR" },
                { "parm_up_cursor", "cuu", "UP" },
                { "parm_up_micro", "mcuu", "Zi" },
                { "pkey_key", "pfkey", "pk" },
                { "pkey_local", "pfloc", "pl" },
                { "pkey_xmit", "pfx", "px" },
                { "plab_norm", "pln", "pn" },
                { "print_screen", "mc0", "ps" },
                { "prtr_non", "mc5p", "pO" },
                { "prtr_off", "mc4", "pf" },
                { "prtr_on", "mc5", "po" },
                { "pulse", "pulse", "PU" },
                { "quick_dial", "qdial", "QD" },
                { "remove_clock", "rmclk", "RC" },
                { "repeat_char", "rep", "rp" },
                { "req_for_input", "rfi", "RF" },
                { "reset_1string", "rs1", "r1" },
                { "reset_2string", "rs2", "r2" },
                { "reset_3string", "rs3", "r3" },
                { "reset_file", "rf", "rf" },
                { "restore_cursor", "rc", "rc" },
                { "row_address", "vpa", "cv" },
                { "save_cursor", "sc", "sc" },
                { "scroll_forward", "ind", "sf" },
                { "scroll_reverse", "ri", "sr" },
                { "select_char_set", "scs", "Zj" },
                { "set_attributes", "sgr", "sa" },
                { "set_background", "setb", "Sb" },
                { "set_bottom_margin", "smgb", "Zk" },
                { "set_bottom_margin_parm", "smgbp", "Zl" },
                { "set_clock", "sclk", "SC" },
                { "set_color_pair", "scp", "sp" },
                { "set_foreground", "setf", "Sf" },
                { "set_left_margin", "smgl", "ML" },
                { "set_left_margin_parm", "smglp", "Zm" },
                { "set_right_margin", "smgr", "MR" },
                { "set_right_margin_parm", "smgrp", "Zn" },
                { "set_tab", "hts", "st" },
                { "set_top_margin", "smgt", "Zo" },
                { "set_top_margin_parm", "smgtp", "Zp" },
                { "set_window", "wind", "wi" },
                { "start_bit_image", "sbim", "Zq" },
                { "start_char_set_def", "scsd", "Zr" },
                { "stop_bit_image", "rbim", "Zs" },
                { "stop_char_set_def", "rcsd", "Zt" },
                { "subscript_characters", "subcs", "Zu" },
                { "superscript_characters", "supcs", "Zv" },
                { "tab", "ht", "ta" },
                { "these_cause_cr", "docr", "Zw" },
                { "to_status_line", "tsl", "ts" },
                { "tone", "tone", "TO" },
                { "underline_char", "uc", "uc" },
                { "up_half_line", "hu", "hu" },
                { "user0", "u0", "u0" },
                { "user1", "u1", "u1" },
                { "user2", "u2", "u2" },
                { "user3", "u3", "u3" },
                { "user4", "u4", "u4" },
                { "user5", "u5", "u5" },
                { "user6", "u6", "u6" },
                { "user7", "u7", "u7" },
                { "user8", "u8", "u8" },
                { "user9", "u9", "u9" },
                { "wait_tone", "wait", "WA" },
                { "xoff_character", "xoffc", "XF" },
                { "xon_character", "xonc", "XN" },
                { "zero_motion", "zerom", "Zx" },
                { "alt_scancode_esc", "scesa", "S8" },
                { "bit_image_carriage_return", "bicr", "Yv" },
                { "bit_image_newline", "binel", "Zz" },
                { "bit_image_repeat", "birep", "Xy" },
                { "char_set_names", "csnm", "Zy" },
                { "code_set_init", "csin", "ci" },
                { "color_names", "colornm", "Yw" },
                { "define_bit_image_region", "defbi", "Yx" },
                { "device_type", "devt", "dv" },
                { "display_pc_char", "dispc", "S1" },
                { "end_bit_image_region", "endbi", "Yy" },
                { "enter_pc_charset_mode", "smpch", "S2" },
                { "enter_scancode_mode", "smsc", "S4" },
                { "exit_pc_charset_mode", "rmpch", "S3" },
                { "exit_scancode_mode", "rmsc", "S5" },
                { "get_mouse", "getm", "Gm" },
                { "key_mouse", "kmous", "Km" },
                { "mouse_info", "minfo", "Mi" },
                { "pc_term_options", "pctrm", "S6" },
                { "pkey_plab", "pfxl", "xl" },
                { "req_mouse_pos", "reqmp", "RQ" },
                { "scancode_escape", "scesc", "S7" },
                { "set0_des_seq", "s0ds", "s0" },
                { "set1_des_seq", "s1ds", "s1" },
                { "set2_des_seq", "s2ds", "s2" },
                { "set3_des_seq", "s3ds", "s3" },
                { "set_a_background", "setab", "AB" },
                { "set_a_foreground", "setaf", "AF" },
                { "set_color_band", "setcolor", "Yz" },
                { "set_lr_margin", "smglr", "ML" },
                { "set_page_length", "slines", "YZ" },
                { "set_tb_margin", "smgtb", "MT" },
                { "enter_horizontal_hl_mode", "ehhlm", "Xh" },
                { "enter_left_hl_mode", "elhlm", "Xl" },
                { "enter_low_hl_mode", "elohlm", "Xo" },
                { "enter_right_hl_mode", "erhlm", "Xr" },
                { "enter_top_hl_mode", "ethlm", "Xt" },
                { "enter_vertical_hl_mode", "evhlm", "Xv" },
                { "set_a_attributes", "sgr1", "sA" },
                { "set_pglen_inch", "slength", "sL" }
        };

        Map<String, String[]> map = new HashMap<String, String[]>();
        for (String[] names : list) {
            for (String name : names) {
                map.put(name, names);
            }
        }
        NAMES = Collections.unmodifiableMap(map);
    }

    private static String ANSI_CAPS =
            "#\tReconstructed via infocmp from file: /usr/share/terminfo/61/ansi\n" +
            "ansi|ansi/pc-term compatible with color,\n" +
            "\tam, mc5i, mir, msgr,\n" +
            "\tcolors#8, cols#80, it#8, lines#24, ncv#3, pairs#64,\n" +
            "\tacsc=+\\020\\,\\021-\\030.^Y0\\333`\\004a\\261f\\370g\\361h\\260j\\331k\\277l\\332m\\300n\\305o~p\\304q\\304r\\304s_t\\303u\\264v\\301w\\302x\\263y\\363z\\362{\\343|\\330}\\234~\\376,\n" +
            "\tbel=^G, blink=\\E[5m, bold=\\E[1m, cbt=\\E[Z, clear=\\E[H\\E[J,\n" +
            "\tcr=^M, cub=\\E[%p1%dD, cub1=\\E[D, cud=\\E[%p1%dB, cud1=\\E[B,\n" +
            "\tcuf=\\E[%p1%dC, cuf1=\\E[C, cup=\\E[%i%p1%d;%p2%dH,\n" +
            "\tcuu=\\E[%p1%dA, cuu1=\\E[A, dch=\\E[%p1%dP, dch1=\\E[P,\n" +
            "\tdl=\\E[%p1%dM, dl1=\\E[M, ech=\\E[%p1%dX, ed=\\E[J, el=\\E[K,\n" +
            "\tel1=\\E[1K, home=\\E[H, hpa=\\E[%i%p1%dG, ht=\\E[I, hts=\\EH,\n" +
            "\tich=\\E[%p1%d@, il=\\E[%p1%dL, il1=\\E[L, ind=^J,\n" +
            "\tindn=\\E[%p1%dS, invis=\\E[8m, kbs=^H, kcbt=\\E[Z, kcub1=\\E[D,\n" +
            "\tkcud1=\\E[B, kcuf1=\\E[C, kcuu1=\\E[A, khome=\\E[H, kich1=\\E[L,\n" +
            "\tmc4=\\E[4i, mc5=\\E[5i, nel=\\r\\E[S, op=\\E[39;49m,\n" +
            "\trep=%p1%c\\E[%p2%{1}%-%db, rev=\\E[7m, rin=\\E[%p1%dT,\n" +
            "\trmacs=\\E[10m, rmpch=\\E[10m, rmso=\\E[m, rmul=\\E[m,\n" +
            "\ts0ds=\\E(B, s1ds=\\E)B, s2ds=\\E*B, s3ds=\\E+B,\n" +
            "\tsetab=\\E[4%p1%dm, setaf=\\E[3%p1%dm,\n" +
            "\tsgr=\\E[0;10%?%p1%t;7%;%?%p2%t;4%;%?%p3%t;7%;%?%p4%t;5%;%?%p6%t;1%;%?%p7%t;8%;%?%p9%t;11%;m,\n" +
            "\tsgr0=\\E[0;10m, smacs=\\E[11m, smpch=\\E[11m, smso=\\E[7m,\n" +
            "\tsmul=\\E[4m, tbc=\\E[2g, u6=\\E[%i%d;%dR, u7=\\E[6n,\n" +
            "\tu8=\\E[?%[;0123456789]c, u9=\\E[c, vpa=\\E[%i%p1%dd,";
}
