// created by jay 1.0.2 (c) 2002-2004 ats@cs.rit.edu
// skeleton Java 1.0 (c) 2002 ats@cs.rit.edu

					// line 2 "Ruby19Parser.y"
/***** BEGIN LICENSE BLOCK *****
 * Version: CPL 1.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Common Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/cpl-v10.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2008-2009 Thomas E Enebo <enebo@acm.org>
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the CPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the CPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/
package org.jruby.parser;

import java.io.IOException;

import org.jruby.ast.AliasNode;
import org.jruby.ast.ArgsNode;
import org.jruby.ast.ArgumentNode;
import org.jruby.ast.ArrayNode;
import org.jruby.ast.AssignableNode;
import org.jruby.ast.BackRefNode;
import org.jruby.ast.BeginNode;
import org.jruby.ast.BlockAcceptingNode;
import org.jruby.ast.BlockArgNode;
import org.jruby.ast.BlockNode;
import org.jruby.ast.BlockPassNode;
import org.jruby.ast.BreakNode;
import org.jruby.ast.CaseNode;
import org.jruby.ast.ClassNode;
import org.jruby.ast.ClassVarNode;
import org.jruby.ast.Colon3Node;
import org.jruby.ast.ConstDeclNode;
import org.jruby.ast.DRegexpNode;
import org.jruby.ast.DStrNode;
import org.jruby.ast.DSymbolNode;
import org.jruby.ast.DXStrNode;
import org.jruby.ast.DefinedNode;
import org.jruby.ast.DefnNode;
import org.jruby.ast.DefsNode;
import org.jruby.ast.DotNode;
import org.jruby.ast.EnsureNode;
import org.jruby.ast.EvStrNode;
import org.jruby.ast.FCallNoArgBlockNode;
import org.jruby.ast.FCallNoArgNode;
import org.jruby.ast.FixnumNode;
import org.jruby.ast.FloatNode;
import org.jruby.ast.ForNode;
import org.jruby.ast.GlobalVarNode;
import org.jruby.ast.HashNode;
import org.jruby.ast.IfNode;
import org.jruby.ast.InstVarNode;
import org.jruby.ast.IterNode;
import org.jruby.ast.LambdaNode;
import org.jruby.ast.ListNode;
import org.jruby.ast.ModuleNode;
import org.jruby.ast.MultipleAsgn19Node;
import org.jruby.ast.NewlineNode;
import org.jruby.ast.NextNode;
import org.jruby.ast.NilImplicitNode;
import org.jruby.ast.NilNode;
import org.jruby.ast.Node;
import org.jruby.ast.NotNode;
import org.jruby.ast.OpAsgnAndNode;
import org.jruby.ast.OpAsgnNode;
import org.jruby.ast.OpAsgnOrNode;
import org.jruby.ast.OptArgNode;
import org.jruby.ast.PostExeNode;
import org.jruby.ast.PreExeNode;
import org.jruby.ast.RedoNode;
import org.jruby.ast.RegexpNode;
import org.jruby.ast.RescueBodyNode;
import org.jruby.ast.RescueNode;
import org.jruby.ast.RestArgNode;
import org.jruby.ast.RetryNode;
import org.jruby.ast.ReturnNode;
import org.jruby.ast.SClassNode;
import org.jruby.ast.SValueNode;
import org.jruby.ast.SelfNode;
import org.jruby.ast.SplatNode;
import org.jruby.ast.StarNode;
import org.jruby.ast.StrNode;
import org.jruby.ast.SymbolNode;
import org.jruby.ast.ToAryNode;
import org.jruby.ast.TypedArgumentNode;
import org.jruby.ast.UndefNode;
import org.jruby.ast.UnnamedRestArgNode;
import org.jruby.ast.UntilNode;
import org.jruby.ast.VAliasNode;
import org.jruby.ast.WhileNode;
import org.jruby.ast.XStrNode;
import org.jruby.ast.YieldNode;
import org.jruby.ast.ZArrayNode;
import org.jruby.ast.ZSuperNode;
import org.jruby.ast.types.ILiteralNode;
import org.jruby.common.IRubyWarnings;
import org.jruby.common.IRubyWarnings.ID;
import org.jruby.lexer.yacc.ISourcePosition;
import org.jruby.lexer.yacc.ISourcePositionHolder;
import org.jruby.lexer.yacc.LexerSource;
import org.jruby.lexer.yacc.RubyYaccLexer;
import org.jruby.lexer.yacc.RubyYaccLexer.LexState;
import org.jruby.lexer.yacc.StrTerm;
import org.jruby.lexer.yacc.SyntaxException;
import org.jruby.lexer.yacc.SyntaxException.PID;
import org.jruby.lexer.yacc.Token;
import org.jruby.util.ByteList;

public class Ruby19Parser implements RubyParser {
    protected ParserSupport19 support;
    protected RubyYaccLexer lexer;
    protected IRubyWarnings warnings;

    public Ruby19Parser() {
        this(new ParserSupport19());
    }

    public Ruby19Parser(ParserSupport19 support) {
        this.support = support;
        lexer = new RubyYaccLexer(false);
        lexer.setParserSupport(support);
        support.setLexer(lexer);
    }

    public void setWarnings(IRubyWarnings warnings) {
        this.warnings = warnings;

        support.setWarnings(warnings);
        lexer.setWarnings(warnings);
    }
					// line 151 "-"
  // %token constants
  public static final int kCLASS = 257;
  public static final int kMODULE = 258;
  public static final int kDEF = 259;
  public static final int kUNDEF = 260;
  public static final int kBEGIN = 261;
  public static final int kRESCUE = 262;
  public static final int kENSURE = 263;
  public static final int kEND = 264;
  public static final int kIF = 265;
  public static final int kUNLESS = 266;
  public static final int kTHEN = 267;
  public static final int kELSIF = 268;
  public static final int kELSE = 269;
  public static final int kCASE = 270;
  public static final int kWHEN = 271;
  public static final int kWHILE = 272;
  public static final int kUNTIL = 273;
  public static final int kFOR = 274;
  public static final int kBREAK = 275;
  public static final int kNEXT = 276;
  public static final int kREDO = 277;
  public static final int kRETRY = 278;
  public static final int kIN = 279;
  public static final int kDO = 280;
  public static final int kDO_COND = 281;
  public static final int kDO_BLOCK = 282;
  public static final int kRETURN = 283;
  public static final int kYIELD = 284;
  public static final int kSUPER = 285;
  public static final int kSELF = 286;
  public static final int kNIL = 287;
  public static final int kTRUE = 288;
  public static final int kFALSE = 289;
  public static final int kAND = 290;
  public static final int kOR = 291;
  public static final int kNOT = 292;
  public static final int kIF_MOD = 293;
  public static final int kUNLESS_MOD = 294;
  public static final int kWHILE_MOD = 295;
  public static final int kUNTIL_MOD = 296;
  public static final int kRESCUE_MOD = 297;
  public static final int kALIAS = 298;
  public static final int kDEFINED = 299;
  public static final int klBEGIN = 300;
  public static final int klEND = 301;
  public static final int k__LINE__ = 302;
  public static final int k__FILE__ = 303;
  public static final int k__ENCODING__ = 304;
  public static final int kDO_LAMBDA = 305;
  public static final int tIDENTIFIER = 306;
  public static final int tFID = 307;
  public static final int tGVAR = 308;
  public static final int tIVAR = 309;
  public static final int tCONSTANT = 310;
  public static final int tCVAR = 311;
  public static final int tLABEL = 312;
  public static final int tCHAR = 313;
  public static final int tUPLUS = 314;
  public static final int tUMINUS = 315;
  public static final int tUMINUS_NUM = 316;
  public static final int tPOW = 317;
  public static final int tCMP = 318;
  public static final int tEQ = 319;
  public static final int tEQQ = 320;
  public static final int tNEQ = 321;
  public static final int tGEQ = 322;
  public static final int tLEQ = 323;
  public static final int tANDOP = 324;
  public static final int tOROP = 325;
  public static final int tMATCH = 326;
  public static final int tNMATCH = 327;
  public static final int tDOT = 328;
  public static final int tDOT2 = 329;
  public static final int tDOT3 = 330;
  public static final int tAREF = 331;
  public static final int tASET = 332;
  public static final int tLSHFT = 333;
  public static final int tRSHFT = 334;
  public static final int tCOLON2 = 335;
  public static final int tCOLON3 = 336;
  public static final int tOP_ASGN = 337;
  public static final int tASSOC = 338;
  public static final int tLPAREN = 339;
  public static final int tLPAREN2 = 340;
  public static final int tRPAREN = 341;
  public static final int tLPAREN_ARG = 342;
  public static final int tLBRACK = 343;
  public static final int tRBRACK = 344;
  public static final int tLBRACE = 345;
  public static final int tLBRACE_ARG = 346;
  public static final int tSTAR = 347;
  public static final int tSTAR2 = 348;
  public static final int tAMPER = 349;
  public static final int tAMPER2 = 350;
  public static final int tTILDE = 351;
  public static final int tPERCENT = 352;
  public static final int tDIVIDE = 353;
  public static final int tPLUS = 354;
  public static final int tMINUS = 355;
  public static final int tLT = 356;
  public static final int tGT = 357;
  public static final int tPIPE = 358;
  public static final int tBANG = 359;
  public static final int tCARET = 360;
  public static final int tLCURLY = 361;
  public static final int tRCURLY = 362;
  public static final int tBACK_REF2 = 363;
  public static final int tSYMBEG = 364;
  public static final int tSTRING_BEG = 365;
  public static final int tXSTRING_BEG = 366;
  public static final int tREGEXP_BEG = 367;
  public static final int tWORDS_BEG = 368;
  public static final int tQWORDS_BEG = 369;
  public static final int tSTRING_DBEG = 370;
  public static final int tSTRING_DVAR = 371;
  public static final int tSTRING_END = 372;
  public static final int tLAMBDA = 373;
  public static final int tLAMBEG = 374;
  public static final int tNTH_REF = 375;
  public static final int tBACK_REF = 376;
  public static final int tSTRING_CONTENT = 377;
  public static final int tINTEGER = 378;
  public static final int tFLOAT = 379;
  public static final int tREGEXP_END = 380;
  public static final int tLOWEST = 381;
  public static final int yyErrorCode = 256;

  /** number of final state.
    */
  protected static final int yyFinal = 1;

  /** parser tables.
      Order is mandated by <i>jay</i>.
    */
  protected static final short[] yyLhs = {
//yyLhs 541
    -1,   117,     0,    35,    34,    36,    36,    36,    36,   120,
    37,    37,    37,    37,    37,    37,    37,    37,    37,    37,
   121,    37,    37,    37,    37,    37,    37,    37,    37,    37,
    37,    37,    37,    37,    37,    38,    38,    38,    38,    38,
    38,    42,    33,    33,    33,    33,    33,    56,    56,    56,
   122,    95,    41,    41,    41,    41,    41,    41,    41,    41,
    96,    96,   105,   105,    97,    97,    97,    97,    97,    97,
    97,    97,    97,    97,    68,    68,    82,    82,    86,    86,
    69,    69,    69,    69,    69,    69,    69,    69,    74,    74,
    74,    74,    74,    74,    74,    74,     8,     8,    32,    32,
    32,     9,     9,     9,     9,     9,   112,   112,     2,     2,
    58,   123,    58,    10,    10,    10,    10,    10,    10,    10,
    10,    10,    10,    10,    10,    10,    10,    10,    10,    10,
    10,    10,    10,    10,    10,    10,    10,    10,    10,    10,
    10,    10,   115,   115,   115,   115,   115,   115,   115,   115,
   115,   115,   115,   115,   115,   115,   115,   115,   115,   115,
   115,   115,   115,   115,   115,   115,   115,   115,   115,   115,
   115,   115,   115,   115,   115,   115,   115,   115,   115,   115,
   115,   115,   115,   115,    39,    39,    39,    39,    39,    39,
    39,    39,    39,    39,    39,    39,    39,    39,    39,    39,
    39,    39,    39,    39,    39,    39,    39,    39,    39,    39,
    39,    39,    39,    39,    39,    39,    39,    39,    39,    39,
    39,    39,    39,    39,    39,    39,    39,    39,    39,    70,
    73,    73,    73,    73,    50,    54,    54,   108,   108,    48,
    48,    48,    48,    48,   126,    52,    89,    88,    88,    76,
    76,    76,    76,    67,    67,    67,    40,    40,    40,    40,
    40,    40,    40,    40,    40,    40,   127,    40,    40,    40,
    40,    40,    40,    40,    40,    40,    40,    40,    40,    40,
    40,    40,    40,    40,    40,    40,   129,   131,    40,   132,
   133,    40,    40,    40,   134,   135,    40,   136,    40,   138,
   139,    40,   140,    40,   141,    40,   142,   143,    40,    40,
    40,    40,    40,    43,   128,   128,   128,   130,   130,    46,
    46,    44,    44,   107,   107,   109,   109,    81,    81,   110,
   110,   110,   110,   110,   110,   110,   110,   110,    64,    64,
    64,    64,    64,    64,    64,    64,    64,    64,    64,    64,
    64,    64,    64,    66,    66,    65,    65,    65,   102,   102,
   101,   101,   111,   111,   144,   104,    63,    63,   103,   103,
   145,    94,    55,    55,    55,    25,    25,    25,    25,    25,
    25,    25,    25,    25,   146,    93,   147,    93,    71,    45,
    45,    98,    98,    72,    72,    72,    47,    47,    49,    49,
    29,    29,    29,    17,    18,    18,    18,    19,    20,    21,
    26,    26,    78,    78,    28,    28,    27,    27,    77,    77,
    22,    22,    23,    23,    24,   148,    24,   149,    24,    59,
    59,    59,    59,     4,     3,     3,     3,     3,    31,    30,
    30,    30,    30,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,    53,    99,    60,    60,    51,
   150,    51,    51,    62,    62,    61,    61,    61,    61,    61,
    61,    61,    61,    61,    61,    61,    61,    61,    61,    61,
   116,   116,   116,   116,    11,    11,   100,   100,    79,    79,
    57,   106,    87,    87,    80,    80,    13,    13,    15,    15,
    14,    14,    92,    91,    91,    16,   151,    16,    85,    85,
    83,    83,    84,    84,     5,     5,     5,     6,     6,     6,
     6,     7,     7,     7,    12,    12,   118,   118,   124,   124,
   113,   114,   125,   125,   125,   137,   137,   119,   119,    75,
    90,
    }, yyLen = {
//yyLen 541
     2,     0,     2,     4,     2,     1,     1,     3,     2,     0,
     4,     3,     3,     3,     2,     3,     3,     3,     3,     3,
     0,     5,     4,     3,     3,     3,     6,     5,     5,     5,
     3,     3,     3,     3,     1,     1,     3,     3,     2,     2,
     1,     1,     1,     1,     2,     2,     2,     1,     4,     4,
     0,     5,     2,     3,     4,     5,     4,     5,     2,     2,
     1,     3,     1,     3,     1,     2,     3,     5,     2,     4,
     2,     4,     1,     3,     1,     3,     2,     3,     1,     3,
     1,     4,     3,     3,     3,     3,     2,     1,     1,     4,
     3,     3,     3,     3,     2,     1,     1,     1,     2,     1,
     3,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     0,     4,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     3,     5,     3,     5,     6,     5,
     5,     5,     5,     4,     3,     3,     3,     3,     3,     3,
     3,     3,     3,     4,     4,     2,     2,     3,     3,     3,
     3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
     2,     2,     3,     3,     3,     3,     3,     6,     1,     1,
     1,     2,     4,     2,     3,     1,     1,     1,     1,     1,
     2,     2,     4,     1,     0,     2,     2,     2,     1,     1,
     2,     3,     4,     3,     4,     2,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     3,     0,     4,     3,     3,
     2,     3,     3,     1,     4,     3,     1,     5,     4,     3,
     2,     1,     2,     2,     6,     6,     0,     0,     7,     0,
     0,     7,     5,     4,     0,     0,     9,     0,     6,     0,
     0,     8,     0,     5,     0,     6,     0,     0,     9,     1,
     1,     1,     1,     1,     1,     1,     2,     1,     1,     1,
     5,     1,     2,     1,     1,     1,     3,     1,     3,     1,
     4,     6,     3,     5,     2,     4,     1,     3,     6,     8,
     4,     6,     4,     2,     6,     2,     4,     6,     2,     4,
     2,     4,     1,     1,     1,     3,     1,     4,     1,     2,
     1,     3,     1,     1,     0,     3,     4,     2,     3,     3,
     0,     5,     2,     4,     4,     2,     4,     4,     3,     3,
     3,     2,     1,     4,     0,     5,     0,     5,     5,     1,
     1,     6,     0,     1,     1,     1,     2,     1,     2,     1,
     1,     1,     1,     1,     1,     1,     2,     3,     3,     3,
     3,     3,     0,     3,     1,     2,     3,     3,     0,     3,
     0,     2,     0,     2,     1,     0,     3,     0,     4,     1,
     1,     1,     1,     2,     1,     1,     1,     1,     3,     1,
     1,     2,     2,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     0,     4,     2,     3,     2,     6,     8,     4,     6,     4,
     6,     2,     4,     6,     2,     4,     2,     4,     1,     0,
     1,     1,     1,     1,     1,     1,     1,     3,     1,     3,
     3,     3,     1,     3,     1,     3,     1,     1,     2,     1,
     1,     1,     2,     2,     0,     1,     0,     4,     1,     2,
     1,     3,     3,     2,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     0,     1,     0,     1,
     2,     2,     0,     1,     1,     1,     1,     1,     2,     0,
     0,
    }, yyDefRed = {
//yyDefRed 945
     1,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   286,   289,     0,     0,     0,   311,   312,     0,
     0,     0,   449,   448,   450,   451,     0,     0,     0,    20,
     0,   453,   452,   454,     0,     0,   445,   444,     0,   447,
   404,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   420,   422,   422,     0,     0,   364,   457,
   458,   439,   440,     0,   401,     0,   257,     0,   405,   258,
   259,     0,   260,   261,   256,   400,   402,    35,     2,     0,
     0,     0,     0,     0,     0,     0,   262,     0,    43,     0,
     0,    74,     0,     5,     0,     0,    60,     0,     0,   309,
   310,   273,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   455,     0,    99,     0,   313,     0,   263,   302,
   152,   163,   153,   176,   149,   169,   159,   158,   174,   157,
   156,   151,   177,   161,   150,   164,   168,   170,   162,   155,
   171,   178,   173,     0,     0,     0,     0,   148,   167,   166,
   179,   180,   181,   182,   183,   147,   154,   145,   146,     0,
     0,     0,     0,   103,     0,   137,   138,   134,   116,   117,
   118,   125,   122,   124,   119,   120,   139,   140,   126,   127,
   506,   131,   130,   115,   136,   133,   132,   128,   129,   123,
   121,   113,   135,   114,   141,   304,   104,     0,   505,   105,
   172,   165,   175,   160,   142,   143,   144,   101,   102,   110,
   107,   106,   109,     0,   108,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   535,   536,     0,     0,
     0,   537,     0,     0,     0,     0,     0,     0,   323,   324,
     0,     0,     0,     0,     0,     0,   239,    45,     0,     0,
     0,   510,   243,    46,    44,     0,    59,     0,     0,   381,
    58,     0,    38,     0,     9,   529,     0,     0,     0,   205,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   230,     0,     0,     0,   508,     0,     0,     0,     0,
     0,     0,     0,     0,   221,    39,   220,   436,   435,   437,
   433,   434,     0,     0,     0,     0,     0,     0,     0,     0,
   283,     0,   386,   384,   375,     0,   280,   406,   282,     4,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   370,   372,     0,     0,     0,     0,
     0,     0,    76,     0,     0,     0,     0,     0,     0,     0,
   441,   442,     0,    96,     0,    98,     0,   460,   297,   459,
     0,     0,     0,     0,     0,     0,   524,   525,   306,   111,
     0,     0,   265,     0,   315,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   538,     0,     0,
     0,     0,     0,     0,   294,   513,   250,   246,     0,     0,
   240,   248,     0,   241,     0,   275,     0,   245,   238,   237,
     0,     0,   279,    11,    13,    12,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   268,     0,     0,
   271,     0,   533,   231,     0,   233,   509,   272,     0,    78,
     0,     0,     0,     0,     0,   427,   425,   438,   424,   423,
   407,   421,   408,   409,   410,   411,   414,     0,   416,   417,
     0,     0,   482,   481,   480,   483,     0,     0,   497,   496,
   501,   500,   486,     0,     0,     0,   494,     0,     0,     0,
     0,   478,   488,   484,     0,     0,    50,    53,     0,    15,
    16,    17,    18,    19,    36,    37,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   519,     0,     0,   520,   379,     0,
     0,     0,     0,   378,     0,   380,     0,   517,   518,     0,
     0,    30,     0,     0,    23,     0,    31,   249,     0,     0,
     0,     0,    77,    24,    33,     0,    25,     0,     0,   462,
     0,     0,     0,     0,     0,     0,   100,     0,     0,     0,
     0,     0,     0,     0,     0,   394,     0,     0,   395,     0,
     0,   321,     0,   316,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   293,   318,   287,   317,   290,     0,     0,
     0,     0,     0,     0,   512,     0,     0,     0,   247,   511,
   274,   530,   234,   278,    10,     0,     0,    22,     0,     0,
     0,     0,   267,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   413,   415,   419,     0,   485,     0,     0,
   325,     0,   327,     0,     0,   498,   502,     0,   476,     0,
   358,   367,     0,     0,   365,     0,   471,     0,   474,   356,
     0,   354,     0,   353,     0,     0,     0,     0,     0,     0,
   236,     0,   376,   235,     0,     0,   377,     0,     0,     0,
    48,   373,    49,   374,     0,     0,     0,    75,     0,     0,
     0,   300,     0,     0,   383,   303,   507,     0,   464,     0,
   307,   112,     0,     0,   397,   322,     0,     3,   399,     0,
   319,     0,     0,     0,     0,     0,     0,   292,     0,     0,
     0,     0,     0,     0,   252,   242,   277,    21,   232,    79,
     0,     0,   429,   430,   431,   426,   432,   490,     0,     0,
     0,     0,   487,     0,     0,   503,   362,     0,   360,   363,
     0,     0,     0,     0,   489,     0,   495,     0,     0,     0,
     0,     0,     0,   352,     0,   492,     0,     0,     0,     0,
     0,    27,     0,    28,     0,    55,    29,     0,     0,    57,
     0,   531,     0,     0,     0,     0,     0,     0,   461,   298,
   463,   305,     0,     0,     0,     0,     0,   396,     0,   398,
     0,   284,     0,   285,   251,     0,     0,     0,   295,   428,
   326,     0,     0,     0,   328,   366,     0,   477,     0,   369,
   368,     0,   469,     0,   467,     0,   472,   475,     0,     0,
   350,     0,     0,   345,     0,   348,   355,   387,   385,     0,
     0,   371,    26,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   389,   388,   390,   288,   291,     0,     0,
     0,     0,     0,   361,     0,     0,     0,     0,     0,     0,
     0,   357,     0,     0,     0,     0,   493,    51,   301,     0,
     0,     0,     0,     0,     0,   391,     0,     0,     0,     0,
   470,     0,   465,   468,   473,   270,     0,   351,     0,   342,
     0,   340,     0,   346,   349,   308,     0,   320,   296,     0,
     0,     0,     0,     0,     0,     0,     0,   466,   344,     0,
   338,   341,   347,     0,   339,
    }, yyDgoto = {
//yyDgoto 152
     1,   218,   209,   300,    64,   113,   585,   553,   114,   211,
   547,   492,   388,   493,   494,   495,   197,    66,    67,    68,
    69,    70,   303,   302,   469,    71,    72,    73,   477,    74,
    75,    76,   115,    77,   215,   216,    79,    80,    81,    82,
    83,    84,   220,   270,   730,   874,   731,   723,   428,   727,
   555,   378,   256,    86,   692,    87,    88,   496,   213,   755,
   222,   591,   592,   498,   780,   681,   682,   566,    90,    91,
   248,   406,   597,   280,   223,    93,   249,   309,   307,   499,
   500,   661,    94,   250,   251,   287,   460,   782,   420,   252,
   421,   668,   765,   316,   355,   507,    95,    96,   391,   224,
   502,   767,   671,   674,   310,   278,   785,   240,   430,   662,
   663,   768,   214,   425,   698,   199,   503,     2,   229,   230,
   436,   267,   685,   594,   426,   453,   257,   449,   395,   232,
   615,   740,   233,   741,   623,   878,   581,   396,   578,   807,
   383,   385,   593,   812,   311,   542,   505,   504,   652,   651,
   580,   384,
    }, yySindex = {
//yySindex 945
     0,     0, 14162, 14409, 16746, 17115, 17823, 17715, 14162, 16377,
 16377, 12613,     0,     0, 16869, 14532, 14532,     0,     0, 14532,
  -250,  -238,     0,     0,     0,     0, 15270, 17607,   133,     0,
  -201,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0, 16500, 16500,  -124,  -141, 14286, 16377, 14901, 15393,  5733,
 16500, 16623, 17930,     0,     0,     0,   185,   262,     0,     0,
     0,     0,     0,     0,     0,   -45,     0,   -48,     0,     0,
     0,  -188,     0,     0,     0,     0,     0,     0,     0,   216,
   740,   -22,  4610,     0,    58,   -44,     0,  -224,     0,    11,
   313,     0,   312,     0, 16992,   393,     0,   176,   740,     0,
     0,     0,  -250,  -238,   165,   133,     0,     0,  -101, 16377,
    54, 14162,     0,   -45,     0,     7,     0,   -32,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   -15,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   472,     0,   264,   269,   236,     0,   -22,
    55,   219,   225,   512,   242,    55,     0,     0,   216,   323,
   540,     0, 16377, 16377,   297,     0,   475,     0,     0,     0,
   340, 16500, 16500, 16500, 16500,  4610,     0,     0,   284,   585,
   591,     0,     0,     0,     0,  3181,     0, 14532, 14532,     0,
     0,  4167,     0,   -74,     0,     0, 15516,   288, 14162,     0,
   477,   350,   351,   353,   342, 14286,   337,     0,   133,   -22,
   338,     0,   119,   289,   284,     0,   289,   327,   387, 17238,
     0,   509,     0,   657,     0,     0,     0,     0,     0,     0,
     0,     0,   -90,   444,   542,   273,   331,   554,   347,   171,
     0,  2130,     0,     0,     0,   366,     0,     0,     0,     0,
 14038, 16377, 16377, 16377, 16377, 14409, 16377, 16377, 16500, 16500,
 16500, 16500, 16500, 16500, 16500, 16500, 16500, 16500, 16500, 16500,
 16500, 16500, 16500, 16500, 16500, 16500, 16500, 16500, 16500, 16500,
 16500, 16500, 16500, 16500,     0,     0,  2198,  2643, 14532, 18477,
 18477, 16623,     0, 15639, 14286, 13915,   681, 15639, 16623,   391,
     0,     0,   -22,     0,     0,     0,   216,     0,     0,     0,
  3666,  4740, 14532, 14162, 16377,  2263,     0,     0,     0,     0,
 15762,   467,     0,   342,     0, 14162,   470,  5233,  6353, 14532,
 16500, 16500, 16500, 14162,   323, 15885,   474,     0,    38,    38,
     0, 18092, 18147, 14532,     0,     0,     0,     0, 16500, 14655,
     0,     0, 15024,     0,   133,     0,   398,     0,     0,     0,
   133,    46,     0,     0,     0,     0, 17715, 16377,  4610, 14162,
   386,  5233,  6353, 16500, 16500, 16500,   133,     0,     0,   133,
     0, 15147,     0,     0, 15393,     0,     0,     0,     0,     0,
   715, 18202, 18257, 14532, 17238,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   101,     0,     0,
   735,   701,     0,     0,     0,     0,  1160,  1671,     0,     0,
     0,     0,     0,   462,   476,   739,     0,   725,  -121,   741,
   744,     0,     0,     0,  -183,  -183,     0,     0,   740,     0,
     0,     0,     0,     0,     0,     0,   350,  2770,  2770,  2770,
  2770,  1218,  1218,  4244,  3714,  2770,  2770,  2728,  2728,   728,
   728,   350,  1556,   350,   350,   354,   354,  1218,  1218,  1452,
  1452,  2588,  -183,   459,     0,   460,  -238,     0,     0,   461,
     0,   481,  -238,     0,     0,     0,   133,     0,     0,  -238,
  -238,     0,  4610, 16500,     0,  4679,     0,     0,   755,   133,
 17238,   775,     0,     0,     0,     0,     0,  5170,   216,     0,
 16377, 14162,  -238,     0,     0,  -238,     0,   133,   562,    46,
  1671,   216, 14162, 18037, 17715,     0,     0,   498,     0, 14162,
   575,     0,   341,     0,   517,   519,   520,   481,   133,  4679,
   467,   584,    78,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   133, 16377,     0, 16500,   284,   591,     0,     0,
     0,     0,     0,     0,     0,    46,   496,     0,   350,   350,
  4610,     0,     0,   289, 17238,     0,     0,     0,     0,   133,
   715, 14162,   376,     0,     0,     0, 16500,     0,  1160,   650,
     0,   818,     0,   133,   725,     0,     0,   889,     0,  1023,
     0,     0, 14162, 14162,     0,  1671,     0,  1671,     0,     0,
  1751,     0, 14162,     0, 14162,  -183,   817, 14162, 16623, 16623,
     0,   366,     0,     0, 16623, 16500,     0,   366,   541,   546,
     0,     0,     0,     0,     0, 16500, 16008,     0,   715, 17238,
 16500,     0,   216,   621,     0,     0,     0,   133,     0,   628,
     0,     0, 17361,    55,     0,     0, 14162,     0,     0, 16377,
     0,   633, 16500, 16500, 16500,   567,   641,     0, 16131, 14162,
 14162, 14162,     0,    38,     0,     0,     0,     0,     0,     0,
     0,   549,     0,     0,     0,     0,     0,     0,   133,  1628,
   864,  1615,     0,   133,   871,     0,     0,   877,     0,     0,
   664,   574,   896,   902,     0,   903,     0,   871,   872,   904,
   725,   906,   907,     0,   594,     0,   691,   600, 14162, 16500,
   700,     0,  4610,     0,  4610,     0,     0,  4610,  4610,     0,
 16623,     0,  4610, 16500,     0,   715,  4610, 14162,     0,     0,
     0,     0,  2263,   656,     0,   532,     0,     0, 14162,     0,
    55,     0, 16500,     0,     0,    36,   706,   708,     0,     0,
     0,   930,  1628,   679,     0,     0,   889,     0,  1023,     0,
     0,   889,     0,  1671,     0,   889,     0,     0, 17484,   889,
     0,   618,  2281,     0,  2281,     0,     0,     0,     0,   616,
  4610,     0,     0,  4610,     0,   717, 14162,     0, 18312, 18367,
 14532,   264, 14162,     0,     0,     0,     0,     0, 14162,  1628,
   930,  1628,   935,     0,   871,   938,   871,   871,   673,   560,
   871,     0,   947,   952,   953,   871,     0,     0,     0,   737,
     0,     0,     0,     0,   133,     0,   341,   743,   930,  1628,
     0,   889,     0,     0,     0,     0, 18422,     0,   889,     0,
  2281,     0,   889,     0,     0,     0,     0,     0,     0,   930,
   871,     0,     0,   871,   964,   871,   871,     0,     0,   889,
     0,     0,     0,   871,     0,
    }, yyRindex = {
//yyRindex 945
     0,     0,   168,     0,     0,     0,     0,     0,   294,     0,
     0,   742,     0,     0,     0, 12951, 13057,     0,     0, 13199,
  4495,  4002,     0,     0,     0,     0,     0,     0, 16254,     0,
     0,     0,     0,     0,  1907,  3016,     0,     0,  2030,     0,
     0,     0,     0,     0,     0,   136,     0,   666,   649,    42,
     0,     0,   698,     0,     0,     0,   797,   214,     0,     0,
     0,     0,     0, 13302,     0, 14778,     0,  6717,     0,     0,
     0,  6818,     0,     0,     0,     0,     0,     0,     0,   370,
   761, 11139,  1251,  6962,  1587,     0,     0, 13765,     0, 13416,
     0,     0,     0,     0,    63,     0,     0,     0,  1006,     0,
     0,     0,  7066,  6023,     0,   675, 11578, 11704,     0,     0,
     0,   136,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,  1424,  2146,  2269,  2762,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,  3255,
  3606,  3748,  4241,     0,  5076,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0, 12674,     0,   362,     0,     0,  6137,  5102,
     0,     0,  6469,     0,     0,     0,     0,     0,   742,     0,
   751,     0,     0,     0,     0,   511,     0,   836,     0,     0,
     0,     0,     0,     0,     0,  1651,     0,     0, 13661,  1787,
  1787,     0,     0,     0,     0,   686,     0,     0,    35,     0,
     0,   686,     0,     0,     0,     0,     0,     0,    28,     0,
     0,  7427,  7179,  7311, 13550,   136,     0,   120,   686,   129,
     0,     0,   684,   684,     0,     0,   677,     0,     0,     0,
  1557,     0,  1559,    64,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    53,     0,     0,     0,   843,     0,     0,     0,     0,
   446,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     6,     0,
     0,     0,     0,     0,   136,   116,   139,     0,     0,     0,
     0,     0,   233,     0, 12094,     0,     0,     0,     0,     0,
     0,     0,     6,   294,     0,   241,     0,     0,     0,     0,
    51,   383,     0,  6603,     0,   730, 12220,     0,     0,     6,
     0,     0,     0,    75,     0,     0,     0,     0,     0,     0,
   847,     0,     0,     6,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   686,     0,     0,     0,     0,     0,
   686,   686,     0,     0,     0,     0,     0,     0, 10403,    28,
     0,     0,     0,     0,     0,     0,   686,     0,   169,   686,
     0,   702,     0,     0,   -77,     0,     0,     0,  1774,     0,
   149,     0,     0,     6,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    79,     0,     0,     0,     0,     0,   140,     0,     0,
     0,     0,     0,   117,     0,    50,     0,   -91,     0,    50,
    50,     0,     0,     0, 12352, 12489,     0,     0,  1144,     0,
     0,     0,     0,     0,     0,     0,  7528,  9492,  9614,  9732,
  9829,  9037,  9157,  9915, 10188, 10005, 10102, 10278, 10318,  8457,
  8579,  7643,  8702,  7776,  7891,  8240,  8353,  9277,  9374,  8820,
  8928,   986, 12352,  4856,     0,  4979,  4372,     0,     0,  5349,
  3386,  5472, 14778,     0,  3509,     0,   707,     0,     0,  5595,
  5595,     0, 10499,     0,     0, 12770,     0,     0,     0,   686,
     0,   154,     0,     0,     0, 10226,     0, 11395,     0,     0,
     0,   294,  6271, 11836, 11962,     0,     0,   707,     0,   686,
   141,     0,   294,     0,     0,     0,    47,   197,     0,   571,
   788,     0,   788,     0,  2400,  2523,  2893,  3879,   707, 11456,
   788,     0,     0,     0,     0,     0,     0,     0,   561,  1121,
  1650,   674,   707,     0,     0,     0, 13704,  1787,     0,     0,
     0,     0,     0,     0,     0,   686,     0,     0,  7992,  8108,
 10584,   121,     0,   684,     0,   781,  1149,  1222,  1468,   707,
   181,    28,     0,     0,     0,     0,     0,     0,     0,   142,
     0,   144,     0,   686,    35,     0,     0,     0,     0,     0,
     0,     0,    59,    28,     0,     0,     0,     0,     0,     0,
   695,     0,    59,     0,    28, 12489,     0,    59,     0,     0,
     0,  1363,     0,     0,     0,     0,     0,  4613, 12850,     0,
     0,     0,     0,     0, 11241,     0,     0,     0,   187,     0,
     0,     0,     0,     0,     0,     0,     0,   686,     0,     0,
     0,     0,     0,     0,     0,     0,    59,     0,     0,     0,
     0,     0,     0,     0,     0,  5922,     0,     0,     0,    85,
    59,    59,   850,     0,     0,     0,     0,     0,     0,     0,
  1499,     0,     0,     0,     0,     0,     0,     0,   686,     0,
   146,     0,     0,   686,    50,     0,     0,   118,     0,     0,
     0,     0,    50,    50,     0,    50,     0,    50,   161,    82,
   695,    82,    82,     0,     0,     0,     0,     0,    28,     0,
     0,     0, 10645,     0, 10742,     0,     0, 10828, 10937,     0,
     0,     0, 10998,     0, 13805,   209, 11084,   294,     0,     0,
     0,     0,   241,     0,   736,     0,   764,     0,   294,     0,
     0,     0,     0,     0,     0,   788,     0,     0,     0,     0,
     0,   152,     0,   173,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    86,     0,     0,     0,     0,     0,     0,     0,
 11181,     0,     0, 11305, 13822,     0,   294,   792,     0,     0,
     6,   362,   730,     0,     0,     0,     0,     0,    59,     0,
   184,     0,   188,     0,    50,    50,    50,    50,     0,   174,
    82,     0,    82,    82,    82,    82,     0,     0,     0,     0,
  1757,  1785,  2148,   614,   707,     0,   788,     0,   198,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   665,     0,     0,   201,
    50,   927,  1281,    82,    82,    82,    82,     0,     0,     0,
     0,     0,     0,    82,     0,
    }, yyGindex = {
//yyGindex 152
     0,   504,   -23,     0,    -5,   177,  -240,     0,   -47,    20,
    -6,  -312,     0,     0,     0,   620,     0,     0,     0,   987,
     0,     0,     0,   607,  -228,     0,     0,     0,     0,     0,
     0,    27,  1051,    33,   406,  -342,     0,   164,  1170,  1338,
    72,    16,    57,     4,  -386,     0,   160,     0,   680,     0,
   195,     0,    18,  1066,   105,     0,     0,  -599,     0,     0,
   655,  -249,   261,     0,     0,     0,  -370,  -126,   -43,   -13,
   799,  -375,     0,     0,    91,   759,   106,     0,     0,  5452,
   402,  -677,     0,   -37,  -197,     0,  -400,   232,  -208,   -87,
     0,   936,  -278,  1015,     0,  -439,  1073,   167,   218,   609,
  -605,     0,  -474,     0,     0,  -175,  -773,     0,  -355,  -613,
   433,   257,     0,   304,  -311,     0,  -598,     0,     1,  1018,
     0,     0,     0,     0,    -4,  -246,     0,     0,  -200,     0,
  -366,     0,     0,     0,     0,     0,     0,    17,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,
    };
    protected static final short[] yyTable = Ruby19YyTables.yyTable();
    protected static final short[] yyCheck = Ruby19YyTables.yyCheck();

  /** maps symbol value to printable name.
      @see #yyExpecting
    */
  protected static final String[] yyNames = {
    "end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,"' '",null,null,null,null,null,
    null,null,null,null,null,null,"','",null,null,null,null,null,null,
    null,null,null,null,null,null,null,"':'","';'",null,"'='",null,"'?'",
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,
    "'['",null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,
    "kCLASS","kMODULE","kDEF","kUNDEF","kBEGIN","kRESCUE","kENSURE",
    "kEND","kIF","kUNLESS","kTHEN","kELSIF","kELSE","kCASE","kWHEN",
    "kWHILE","kUNTIL","kFOR","kBREAK","kNEXT","kREDO","kRETRY","kIN",
    "kDO","kDO_COND","kDO_BLOCK","kRETURN","kYIELD","kSUPER","kSELF",
    "kNIL","kTRUE","kFALSE","kAND","kOR","kNOT","kIF_MOD","kUNLESS_MOD",
    "kWHILE_MOD","kUNTIL_MOD","kRESCUE_MOD","kALIAS","kDEFINED","klBEGIN",
    "klEND","k__LINE__","k__FILE__","k__ENCODING__","kDO_LAMBDA",
    "tIDENTIFIER","tFID","tGVAR","tIVAR","tCONSTANT","tCVAR","tLABEL",
    "tCHAR","tUPLUS","tUMINUS","tUMINUS_NUM","tPOW","tCMP","tEQ","tEQQ",
    "tNEQ","tGEQ","tLEQ","tANDOP","tOROP","tMATCH","tNMATCH","tDOT",
    "tDOT2","tDOT3","tAREF","tASET","tLSHFT","tRSHFT","tCOLON2","tCOLON3",
    "tOP_ASGN","tASSOC","tLPAREN","tLPAREN2","tRPAREN","tLPAREN_ARG",
    "tLBRACK","tRBRACK","tLBRACE","tLBRACE_ARG","tSTAR","tSTAR2","tAMPER",
    "tAMPER2","tTILDE","tPERCENT","tDIVIDE","tPLUS","tMINUS","tLT","tGT",
    "tPIPE","tBANG","tCARET","tLCURLY","tRCURLY","tBACK_REF2","tSYMBEG",
    "tSTRING_BEG","tXSTRING_BEG","tREGEXP_BEG","tWORDS_BEG","tQWORDS_BEG",
    "tSTRING_DBEG","tSTRING_DVAR","tSTRING_END","tLAMBDA","tLAMBEG",
    "tNTH_REF","tBACK_REF","tSTRING_CONTENT","tINTEGER","tFLOAT",
    "tREGEXP_END","tLOWEST",
    };

  /** printable rules for debugging.
    */
  protected static final String [] yyRule = {
    "$accept : program",
    "$$1 :",
    "program : $$1 compstmt",
    "bodystmt : compstmt opt_rescue opt_else opt_ensure",
    "compstmt : stmts opt_terms",
    "stmts : none",
    "stmts : stmt",
    "stmts : stmts terms stmt",
    "stmts : error stmt",
    "$$2 :",
    "stmt : kALIAS fitem $$2 fitem",
    "stmt : kALIAS tGVAR tGVAR",
    "stmt : kALIAS tGVAR tBACK_REF",
    "stmt : kALIAS tGVAR tNTH_REF",
    "stmt : kUNDEF undef_list",
    "stmt : stmt kIF_MOD expr_value",
    "stmt : stmt kUNLESS_MOD expr_value",
    "stmt : stmt kWHILE_MOD expr_value",
    "stmt : stmt kUNTIL_MOD expr_value",
    "stmt : stmt kRESCUE_MOD stmt",
    "$$3 :",
    "stmt : klBEGIN $$3 tLCURLY compstmt tRCURLY",
    "stmt : klEND tLCURLY compstmt tRCURLY",
    "stmt : lhs '=' command_call",
    "stmt : mlhs '=' command_call",
    "stmt : var_lhs tOP_ASGN command_call",
    "stmt : primary_value '[' opt_call_args rbracket tOP_ASGN command_call",
    "stmt : primary_value tDOT tIDENTIFIER tOP_ASGN command_call",
    "stmt : primary_value tDOT tCONSTANT tOP_ASGN command_call",
    "stmt : primary_value tCOLON2 tIDENTIFIER tOP_ASGN command_call",
    "stmt : backref tOP_ASGN command_call",
    "stmt : lhs '=' mrhs",
    "stmt : mlhs '=' arg_value",
    "stmt : mlhs '=' mrhs",
    "stmt : expr",
    "expr : command_call",
    "expr : expr kAND expr",
    "expr : expr kOR expr",
    "expr : kNOT expr",
    "expr : tBANG command_call",
    "expr : arg",
    "expr_value : expr",
    "command_call : command",
    "command_call : block_command",
    "command_call : kRETURN call_args",
    "command_call : kBREAK call_args",
    "command_call : kNEXT call_args",
    "block_command : block_call",
    "block_command : block_call tDOT operation2 command_args",
    "block_command : block_call tCOLON2 operation2 command_args",
    "$$4 :",
    "cmd_brace_block : tLBRACE_ARG $$4 opt_block_param compstmt tRCURLY",
    "command : operation command_args",
    "command : operation command_args cmd_brace_block",
    "command : primary_value tDOT operation2 command_args",
    "command : primary_value tDOT operation2 command_args cmd_brace_block",
    "command : primary_value tCOLON2 operation2 command_args",
    "command : primary_value tCOLON2 operation2 command_args cmd_brace_block",
    "command : kSUPER command_args",
    "command : kYIELD command_args",
    "mlhs : mlhs_basic",
    "mlhs : tLPAREN mlhs_inner rparen",
    "mlhs_inner : mlhs_basic",
    "mlhs_inner : tLPAREN mlhs_inner rparen",
    "mlhs_basic : mlhs_head",
    "mlhs_basic : mlhs_head mlhs_item",
    "mlhs_basic : mlhs_head tSTAR mlhs_node",
    "mlhs_basic : mlhs_head tSTAR mlhs_node ',' mlhs_post",
    "mlhs_basic : mlhs_head tSTAR",
    "mlhs_basic : mlhs_head tSTAR ',' mlhs_post",
    "mlhs_basic : tSTAR mlhs_node",
    "mlhs_basic : tSTAR mlhs_node ',' mlhs_post",
    "mlhs_basic : tSTAR",
    "mlhs_basic : tSTAR ',' mlhs_post",
    "mlhs_item : mlhs_node",
    "mlhs_item : tLPAREN mlhs_inner rparen",
    "mlhs_head : mlhs_item ','",
    "mlhs_head : mlhs_head mlhs_item ','",
    "mlhs_post : mlhs_item",
    "mlhs_post : mlhs_post ',' mlhs_item",
    "mlhs_node : variable",
    "mlhs_node : primary_value '[' opt_call_args rbracket",
    "mlhs_node : primary_value tDOT tIDENTIFIER",
    "mlhs_node : primary_value tCOLON2 tIDENTIFIER",
    "mlhs_node : primary_value tDOT tCONSTANT",
    "mlhs_node : primary_value tCOLON2 tCONSTANT",
    "mlhs_node : tCOLON3 tCONSTANT",
    "mlhs_node : backref",
    "lhs : variable",
    "lhs : primary_value '[' opt_call_args rbracket",
    "lhs : primary_value tDOT tIDENTIFIER",
    "lhs : primary_value tCOLON2 tIDENTIFIER",
    "lhs : primary_value tDOT tCONSTANT",
    "lhs : primary_value tCOLON2 tCONSTANT",
    "lhs : tCOLON3 tCONSTANT",
    "lhs : backref",
    "cname : tIDENTIFIER",
    "cname : tCONSTANT",
    "cpath : tCOLON3 cname",
    "cpath : cname",
    "cpath : primary_value tCOLON2 cname",
    "fname : tIDENTIFIER",
    "fname : tCONSTANT",
    "fname : tFID",
    "fname : op",
    "fname : reswords",
    "fsym : fname",
    "fsym : symbol",
    "fitem : fsym",
    "fitem : dsym",
    "undef_list : fitem",
    "$$5 :",
    "undef_list : undef_list ',' $$5 fitem",
    "op : tPIPE",
    "op : tCARET",
    "op : tAMPER2",
    "op : tCMP",
    "op : tEQ",
    "op : tEQQ",
    "op : tMATCH",
    "op : tNMATCH",
    "op : tGT",
    "op : tGEQ",
    "op : tLT",
    "op : tLEQ",
    "op : tNEQ",
    "op : tLSHFT",
    "op : tRSHFT",
    "op : tPLUS",
    "op : tMINUS",
    "op : tSTAR2",
    "op : tSTAR",
    "op : tDIVIDE",
    "op : tPERCENT",
    "op : tPOW",
    "op : tBANG",
    "op : tTILDE",
    "op : tUPLUS",
    "op : tUMINUS",
    "op : tAREF",
    "op : tASET",
    "op : tBACK_REF2",
    "reswords : k__LINE__",
    "reswords : k__FILE__",
    "reswords : k__ENCODING__",
    "reswords : klBEGIN",
    "reswords : klEND",
    "reswords : kALIAS",
    "reswords : kAND",
    "reswords : kBEGIN",
    "reswords : kBREAK",
    "reswords : kCASE",
    "reswords : kCLASS",
    "reswords : kDEF",
    "reswords : kDEFINED",
    "reswords : kDO",
    "reswords : kELSE",
    "reswords : kELSIF",
    "reswords : kEND",
    "reswords : kENSURE",
    "reswords : kFALSE",
    "reswords : kFOR",
    "reswords : kIN",
    "reswords : kMODULE",
    "reswords : kNEXT",
    "reswords : kNIL",
    "reswords : kNOT",
    "reswords : kOR",
    "reswords : kREDO",
    "reswords : kRESCUE",
    "reswords : kRETRY",
    "reswords : kRETURN",
    "reswords : kSELF",
    "reswords : kSUPER",
    "reswords : kTHEN",
    "reswords : kTRUE",
    "reswords : kUNDEF",
    "reswords : kWHEN",
    "reswords : kYIELD",
    "reswords : kIF_MOD",
    "reswords : kUNLESS_MOD",
    "reswords : kWHILE_MOD",
    "reswords : kUNTIL_MOD",
    "reswords : kRESCUE_MOD",
    "arg : lhs '=' arg",
    "arg : lhs '=' arg kRESCUE_MOD arg",
    "arg : var_lhs tOP_ASGN arg",
    "arg : var_lhs tOP_ASGN arg kRESCUE_MOD arg",
    "arg : primary_value '[' opt_call_args rbracket tOP_ASGN arg",
    "arg : primary_value tDOT tIDENTIFIER tOP_ASGN arg",
    "arg : primary_value tDOT tCONSTANT tOP_ASGN arg",
    "arg : primary_value tCOLON2 tIDENTIFIER tOP_ASGN arg",
    "arg : primary_value tCOLON2 tCONSTANT tOP_ASGN arg",
    "arg : tCOLON3 tCONSTANT tOP_ASGN arg",
    "arg : backref tOP_ASGN arg",
    "arg : arg tDOT2 arg",
    "arg : arg tDOT3 arg",
    "arg : arg tPLUS arg",
    "arg : arg tMINUS arg",
    "arg : arg tSTAR2 arg",
    "arg : arg tDIVIDE arg",
    "arg : arg tPERCENT arg",
    "arg : arg tPOW arg",
    "arg : tUMINUS_NUM tINTEGER tPOW arg",
    "arg : tUMINUS_NUM tFLOAT tPOW arg",
    "arg : tUPLUS arg",
    "arg : tUMINUS arg",
    "arg : arg tPIPE arg",
    "arg : arg tCARET arg",
    "arg : arg tAMPER2 arg",
    "arg : arg tCMP arg",
    "arg : arg tGT arg",
    "arg : arg tGEQ arg",
    "arg : arg tLT arg",
    "arg : arg tLEQ arg",
    "arg : arg tEQ arg",
    "arg : arg tEQQ arg",
    "arg : arg tNEQ arg",
    "arg : arg tMATCH arg",
    "arg : arg tNMATCH arg",
    "arg : tBANG arg",
    "arg : tTILDE arg",
    "arg : arg tLSHFT arg",
    "arg : arg tRSHFT arg",
    "arg : arg tANDOP arg",
    "arg : arg tOROP arg",
    "arg : kDEFINED opt_nl arg",
    "arg : arg '?' arg opt_nl ':' arg",
    "arg : primary",
    "arg_value : arg",
    "aref_args : none",
    "aref_args : args trailer",
    "aref_args : args ',' assocs trailer",
    "aref_args : assocs trailer",
    "paren_args : tLPAREN2 opt_call_args rparen",
    "opt_paren_args : none",
    "opt_paren_args : paren_args",
    "opt_call_args : none",
    "opt_call_args : call_args",
    "call_args : command",
    "call_args : args opt_block_arg",
    "call_args : assocs opt_block_arg",
    "call_args : args ',' assocs opt_block_arg",
    "call_args : block_arg",
    "$$6 :",
    "command_args : $$6 call_args",
    "block_arg : tAMPER arg_value",
    "opt_block_arg : ',' block_arg",
    "opt_block_arg : none_block_pass",
    "args : arg_value",
    "args : tSTAR arg_value",
    "args : args ',' arg_value",
    "args : args ',' tSTAR arg_value",
    "mrhs : args ',' arg_value",
    "mrhs : args ',' tSTAR arg_value",
    "mrhs : tSTAR arg_value",
    "primary : literal",
    "primary : strings",
    "primary : xstring",
    "primary : regexp",
    "primary : words",
    "primary : qwords",
    "primary : var_ref",
    "primary : backref",
    "primary : tFID",
    "primary : kBEGIN bodystmt kEND",
    "$$7 :",
    "primary : tLPAREN_ARG expr $$7 rparen",
    "primary : tLPAREN compstmt tRPAREN",
    "primary : primary_value tCOLON2 tCONSTANT",
    "primary : tCOLON3 tCONSTANT",
    "primary : tLBRACK aref_args tRBRACK",
    "primary : tLBRACE assoc_list tRCURLY",
    "primary : kRETURN",
    "primary : kYIELD tLPAREN2 call_args rparen",
    "primary : kYIELD tLPAREN2 rparen",
    "primary : kYIELD",
    "primary : kDEFINED opt_nl tLPAREN2 expr rparen",
    "primary : kNOT tLPAREN2 expr rparen",
    "primary : kNOT tLPAREN2 rparen",
    "primary : operation brace_block",
    "primary : method_call",
    "primary : method_call brace_block",
    "primary : tLAMBDA lambda",
    "primary : kIF expr_value then compstmt if_tail kEND",
    "primary : kUNLESS expr_value then compstmt opt_else kEND",
    "$$8 :",
    "$$9 :",
    "primary : kWHILE $$8 expr_value do $$9 compstmt kEND",
    "$$10 :",
    "$$11 :",
    "primary : kUNTIL $$10 expr_value do $$11 compstmt kEND",
    "primary : kCASE expr_value opt_terms case_body kEND",
    "primary : kCASE opt_terms case_body kEND",
    "$$12 :",
    "$$13 :",
    "primary : kFOR for_var kIN $$12 expr_value do $$13 compstmt kEND",
    "$$14 :",
    "primary : kCLASS cpath superclass $$14 bodystmt kEND",
    "$$15 :",
    "$$16 :",
    "primary : kCLASS tLSHFT expr $$15 term $$16 bodystmt kEND",
    "$$17 :",
    "primary : kMODULE cpath $$17 bodystmt kEND",
    "$$18 :",
    "primary : kDEF fname $$18 f_arglist bodystmt kEND",
    "$$19 :",
    "$$20 :",
    "primary : kDEF singleton dot_or_colon $$19 fname $$20 f_arglist bodystmt kEND",
    "primary : kBREAK",
    "primary : kNEXT",
    "primary : kREDO",
    "primary : kRETRY",
    "primary_value : primary",
    "then : term",
    "then : kTHEN",
    "then : term kTHEN",
    "do : term",
    "do : kDO_COND",
    "if_tail : opt_else",
    "if_tail : kELSIF expr_value then compstmt if_tail",
    "opt_else : none",
    "opt_else : kELSE compstmt",
    "for_var : lhs",
    "for_var : mlhs",
    "f_marg : f_norm_arg",
    "f_marg : tLPAREN f_margs rparen",
    "f_marg_list : f_marg",
    "f_marg_list : f_marg_list ',' f_marg",
    "f_margs : f_marg_list",
    "f_margs : f_marg_list ',' tSTAR f_norm_arg",
    "f_margs : f_marg_list ',' tSTAR f_norm_arg ',' f_marg_list",
    "f_margs : f_marg_list ',' tSTAR",
    "f_margs : f_marg_list ',' tSTAR ',' f_marg_list",
    "f_margs : tSTAR f_norm_arg",
    "f_margs : tSTAR f_norm_arg ',' f_marg_list",
    "f_margs : tSTAR",
    "f_margs : tSTAR ',' f_marg_list",
    "block_param : f_arg ',' f_block_optarg ',' f_rest_arg opt_f_block_arg",
    "block_param : f_arg ',' f_block_optarg ',' f_rest_arg ',' f_arg opt_f_block_arg",
    "block_param : f_arg ',' f_block_optarg opt_f_block_arg",
    "block_param : f_arg ',' f_block_optarg ',' f_arg opt_f_block_arg",
    "block_param : f_arg ',' f_rest_arg opt_f_block_arg",
    "block_param : f_arg ','",
    "block_param : f_arg ',' f_rest_arg ',' f_arg opt_f_block_arg",
    "block_param : f_arg opt_f_block_arg",
    "block_param : f_block_optarg ',' f_rest_arg opt_f_block_arg",
    "block_param : f_block_optarg ',' f_rest_arg ',' f_arg opt_f_block_arg",
    "block_param : f_block_optarg opt_f_block_arg",
    "block_param : f_block_optarg ',' f_arg opt_f_block_arg",
    "block_param : f_rest_arg opt_f_block_arg",
    "block_param : f_rest_arg ',' f_arg opt_f_block_arg",
    "block_param : f_block_arg",
    "opt_block_param : none",
    "opt_block_param : block_param_def",
    "block_param_def : tPIPE opt_bv_decl tPIPE",
    "block_param_def : tOROP",
    "block_param_def : tPIPE block_param opt_bv_decl tPIPE",
    "opt_bv_decl : none",
    "opt_bv_decl : ';' bv_decls",
    "bv_decls : bvar",
    "bv_decls : bv_decls ',' bvar",
    "bvar : tIDENTIFIER",
    "bvar : f_bad_arg",
    "$$21 :",
    "lambda : $$21 f_larglist lambda_body",
    "f_larglist : tLPAREN2 f_args opt_bv_decl rparen",
    "f_larglist : f_args opt_bv_decl",
    "lambda_body : tLAMBEG compstmt tRCURLY",
    "lambda_body : kDO_LAMBDA compstmt kEND",
    "$$22 :",
    "do_block : kDO_BLOCK $$22 opt_block_param compstmt kEND",
    "block_call : command do_block",
    "block_call : block_call tDOT operation2 opt_paren_args",
    "block_call : block_call tCOLON2 operation2 opt_paren_args",
    "method_call : operation paren_args",
    "method_call : primary_value tDOT operation2 opt_paren_args",
    "method_call : primary_value tCOLON2 operation2 paren_args",
    "method_call : primary_value tCOLON2 operation3",
    "method_call : primary_value tDOT paren_args",
    "method_call : primary_value tCOLON2 paren_args",
    "method_call : kSUPER paren_args",
    "method_call : kSUPER",
    "method_call : primary_value '[' opt_call_args rbracket",
    "$$23 :",
    "brace_block : tLCURLY $$23 opt_block_param compstmt tRCURLY",
    "$$24 :",
    "brace_block : kDO $$24 opt_block_param compstmt kEND",
    "case_body : kWHEN args then compstmt cases",
    "cases : opt_else",
    "cases : case_body",
    "opt_rescue : kRESCUE exc_list exc_var then compstmt opt_rescue",
    "opt_rescue :",
    "exc_list : arg_value",
    "exc_list : mrhs",
    "exc_list : none",
    "exc_var : tASSOC lhs",
    "exc_var : none",
    "opt_ensure : kENSURE compstmt",
    "opt_ensure : none",
    "literal : numeric",
    "literal : symbol",
    "literal : dsym",
    "strings : string",
    "string : tCHAR",
    "string : string1",
    "string : string string1",
    "string1 : tSTRING_BEG string_contents tSTRING_END",
    "xstring : tXSTRING_BEG xstring_contents tSTRING_END",
    "regexp : tREGEXP_BEG xstring_contents tREGEXP_END",
    "words : tWORDS_BEG ' ' tSTRING_END",
    "words : tWORDS_BEG word_list tSTRING_END",
    "word_list :",
    "word_list : word_list word ' '",
    "word : string_content",
    "word : word string_content",
    "qwords : tQWORDS_BEG ' ' tSTRING_END",
    "qwords : tQWORDS_BEG qword_list tSTRING_END",
    "qword_list :",
    "qword_list : qword_list tSTRING_CONTENT ' '",
    "string_contents :",
    "string_contents : string_contents string_content",
    "xstring_contents :",
    "xstring_contents : xstring_contents string_content",
    "string_content : tSTRING_CONTENT",
    "$$25 :",
    "string_content : tSTRING_DVAR $$25 string_dvar",
    "$$26 :",
    "string_content : tSTRING_DBEG $$26 compstmt tRCURLY",
    "string_dvar : tGVAR",
    "string_dvar : tIVAR",
    "string_dvar : tCVAR",
    "string_dvar : backref",
    "symbol : tSYMBEG sym",
    "sym : fname",
    "sym : tIVAR",
    "sym : tGVAR",
    "sym : tCVAR",
    "dsym : tSYMBEG xstring_contents tSTRING_END",
    "numeric : tINTEGER",
    "numeric : tFLOAT",
    "numeric : tUMINUS_NUM tINTEGER",
    "numeric : tUMINUS_NUM tFLOAT",
    "variable : tIDENTIFIER",
    "variable : tIVAR",
    "variable : tGVAR",
    "variable : tCONSTANT",
    "variable : tCVAR",
    "variable : kNIL",
    "variable : kSELF",
    "variable : kTRUE",
    "variable : kFALSE",
    "variable : k__FILE__",
    "variable : k__LINE__",
    "variable : k__ENCODING__",
    "var_ref : variable",
    "var_lhs : variable",
    "backref : tNTH_REF",
    "backref : tBACK_REF",
    "superclass : term",
    "$$27 :",
    "superclass : tLT $$27 expr_value term",
    "superclass : error term",
    "f_arglist : tLPAREN2 f_args rparen",
    "f_arglist : f_args term",
    "f_args : f_arg ',' f_optarg ',' f_rest_arg opt_f_block_arg",
    "f_args : f_arg ',' f_optarg ',' f_rest_arg ',' f_arg opt_f_block_arg",
    "f_args : f_arg ',' f_optarg opt_f_block_arg",
    "f_args : f_arg ',' f_optarg ',' f_arg opt_f_block_arg",
    "f_args : f_arg ',' f_rest_arg opt_f_block_arg",
    "f_args : f_arg ',' f_rest_arg ',' f_arg opt_f_block_arg",
    "f_args : f_arg opt_f_block_arg",
    "f_args : f_optarg ',' f_rest_arg opt_f_block_arg",
    "f_args : f_optarg ',' f_rest_arg ',' f_arg opt_f_block_arg",
    "f_args : f_optarg opt_f_block_arg",
    "f_args : f_optarg ',' f_arg opt_f_block_arg",
    "f_args : f_rest_arg opt_f_block_arg",
    "f_args : f_rest_arg ',' f_arg opt_f_block_arg",
    "f_args : f_block_arg",
    "f_args :",
    "f_bad_arg : tCONSTANT",
    "f_bad_arg : tIVAR",
    "f_bad_arg : tGVAR",
    "f_bad_arg : tCVAR",
    "f_norm_arg : f_bad_arg",
    "f_norm_arg : tIDENTIFIER",
    "f_arg_item : f_norm_arg",
    "f_arg_item : tLPAREN f_margs rparen",
    "f_arg : f_arg_item",
    "f_arg : f_arg ',' f_arg_item",
    "f_opt : tIDENTIFIER '=' arg_value",
    "f_block_opt : tIDENTIFIER '=' primary_value",
    "f_block_optarg : f_block_opt",
    "f_block_optarg : f_block_optarg ',' f_block_opt",
    "f_optarg : f_opt",
    "f_optarg : f_optarg ',' f_opt",
    "restarg_mark : tSTAR2",
    "restarg_mark : tSTAR",
    "f_rest_arg : restarg_mark tIDENTIFIER",
    "f_rest_arg : restarg_mark",
    "blkarg_mark : tAMPER2",
    "blkarg_mark : tAMPER",
    "f_block_arg : blkarg_mark tIDENTIFIER",
    "opt_f_block_arg : ',' f_block_arg",
    "opt_f_block_arg :",
    "singleton : var_ref",
    "$$28 :",
    "singleton : tLPAREN2 $$28 expr rparen",
    "assoc_list : none",
    "assoc_list : assocs trailer",
    "assocs : assoc",
    "assocs : assocs ',' assoc",
    "assoc : arg_value tASSOC arg_value",
    "assoc : tLABEL arg_value",
    "operation : tIDENTIFIER",
    "operation : tCONSTANT",
    "operation : tFID",
    "operation2 : tIDENTIFIER",
    "operation2 : tCONSTANT",
    "operation2 : tFID",
    "operation2 : op",
    "operation3 : tIDENTIFIER",
    "operation3 : tFID",
    "operation3 : op",
    "dot_or_colon : tDOT",
    "dot_or_colon : tCOLON2",
    "opt_terms :",
    "opt_terms : terms",
    "opt_nl :",
    "opt_nl : '\\n'",
    "rparen : opt_nl tRPAREN",
    "rbracket : opt_nl tRBRACK",
    "trailer :",
    "trailer : '\\n'",
    "trailer : ','",
    "term : ';'",
    "term : '\\n'",
    "terms : term",
    "terms : terms ';'",
    "none :",
    "none_block_pass :",
    };

  /** debugging support, requires the package <tt>jay.yydebug</tt>.
      Set to <tt>null</tt> to suppress debugging messages.
    */
  protected jay.yydebug.yyDebug yydebug;

  /** index-checked interface to {@link #yyNames}.
      @param token single character or <tt>%token</tt> value.
      @return token name or <tt>[illegal]</tt> or <tt>[unknown]</tt>.
    */
  public static final String yyName (int token) {
    if (token < 0 || token > yyNames.length) return "[illegal]";
    String name;
    if ((name = yyNames[token]) != null) return name;
    return "[unknown]";
  }

  /** thrown for irrecoverable syntax errors and stack overflow.
      Nested for convenience, does not depend on parser class.
    */
  public static class yyException extends java.lang.Exception {
    private static final long serialVersionUID = 1L;
    public yyException (String message) {
      super(message);
    }
  }

  /** must be implemented by a scanner object to supply input to the parser.
      Nested for convenience, does not depend on parser class.
    */
  public interface yyInput {

    /** move on to next token.
        @return <tt>false</tt> if positioned beyond tokens.
        @throws IOException on input error.
      */
    boolean advance () throws java.io.IOException;

    /** classifies current token.
        Should not be called if {@link #advance()} returned <tt>false</tt>.
        @return current <tt>%token</tt> or single character.
      */
    int token ();

    /** associated with current token.
        Should not be called if {@link #advance()} returned <tt>false</tt>.
        @return value for {@link #token()}.
      */
    Object value ();
  }

  /** simplified error message.
      @see #yyerror(java.lang.String, java.lang.String[])
    */
  public void yyerror (String message) {
    //new Exception().printStackTrace();
    throw new SyntaxException(PID.GRAMMAR_ERROR, getPosition(null), lexer.getCurrentLine(), message);
  }

  /** (syntax) error message.
      Can be overwritten to control message format.
      @param message text to be displayed.
      @param expected list of acceptable tokens, if available.
    */
  public void yyerror (String message, String[] expected, String found) {
    String text = ", unexpected " + found + "\n";
    //new Exception().printStackTrace();
    throw new SyntaxException(PID.GRAMMAR_ERROR, getPosition(null), lexer.getCurrentLine(), text, found);
  }

  /** computes list of expected tokens on error by tracing the tables.
      @param state for which to compute the list.
      @return list of token names.
    */
  protected String[] yyExpecting (int state) {
    int token, n, len = 0;
    boolean[] ok = new boolean[yyNames.length];

    if ((n = yySindex[state]) != 0)
      for (token = n < 0 ? -n : 0;
           token < yyNames.length && n+token < yyTable.length; ++ token)
        if (yyCheck[n+token] == token && !ok[token] && yyNames[token] != null) {
          ++ len;
          ok[token] = true;
        }
    if ((n = yyRindex[state]) != 0)
      for (token = n < 0 ? -n : 0;
           token < yyNames.length && n+token < yyTable.length; ++ token)
        if (yyCheck[n+token] == token && !ok[token] && yyNames[token] != null) {
          ++ len;
          ok[token] = true;
        }

    String result[] = new String[len];
    for (n = token = 0; n < len;  ++ token)
      if (ok[token]) result[n++] = yyNames[token];
    return result;
  }

  /** the generated parser, with debugging messages.
      Maintains a dynamic state and value stack.
      @param yyLex scanner.
      @param yydebug debug message writer implementing <tt>yyDebug</tt>, or <tt>null</tt>.
      @return result of the last reduction, if any.
      @throws yyException on irrecoverable parse error.
    */
  public Object yyparse (RubyYaccLexer yyLex, Object ayydebug)
				throws java.io.IOException, yyException {
    this.yydebug = (jay.yydebug.yyDebug)ayydebug;
    return yyparse(yyLex);
  }

  /** initial size and increment of the state/value stack [default 256].
      This is not final so that it can be overwritten outside of invocations
      of {@link #yyparse}.
    */
  protected int yyMax;

  /** executed at the beginning of a reduce action.
      Used as <tt>$$ = yyDefault($1)</tt>, prior to the user-specified action, if any.
      Can be overwritten to provide deep copy, etc.
      @param first value for <tt>$1</tt>, or <tt>null</tt>.
      @return first.
    */
  protected Object yyDefault (Object first) {
    return first;
  }

  /** the generated parser.
      Maintains a dynamic state and value stack.
      @param yyLex scanner.
      @return result of the last reduction, if any.
      @throws yyException on irrecoverable parse error.
    */
  public Object yyparse (RubyYaccLexer yyLex) throws java.io.IOException, yyException {
    if (yyMax <= 0) yyMax = 256;			// initial size
    int yyState = 0, yyStates[] = new int[yyMax];	// state stack
    Object yyVal = null, yyVals[] = new Object[yyMax];	// value stack
    int yyToken = -1;					// current input
    int yyErrorFlag = 0;				// #tokens to shift

    yyLoop: for (int yyTop = 0;; ++ yyTop) {
      if (yyTop >= yyStates.length) {			// dynamically increase
        int[] i = new int[yyStates.length+yyMax];
        System.arraycopy(yyStates, 0, i, 0, yyStates.length);
        yyStates = i;
        Object[] o = new Object[yyVals.length+yyMax];
        System.arraycopy(yyVals, 0, o, 0, yyVals.length);
        yyVals = o;
      }
      yyStates[yyTop] = yyState;
      yyVals[yyTop] = yyVal;
      if (yydebug != null) yydebug.push(yyState, yyVal);

      yyDiscarded: for (;;) {	// discarding a token does not change stack
        int yyN;
        if ((yyN = yyDefRed[yyState]) == 0) {	// else [default] reduce (yyN)
          if (yyToken < 0) {
            yyToken = yyLex.advance() ? yyLex.token() : 0;
            if (yydebug != null)
              yydebug.lex(yyState, yyToken, yyName(yyToken), yyLex.value());
          }
          if ((yyN = yySindex[yyState]) != 0 && (yyN += yyToken) >= 0
              && yyN < yyTable.length && yyCheck[yyN] == yyToken) {
            if (yydebug != null)
              yydebug.shift(yyState, yyTable[yyN], yyErrorFlag-1);
            yyState = yyTable[yyN];		// shift to yyN
            yyVal = yyLex.value();
            yyToken = -1;
            if (yyErrorFlag > 0) -- yyErrorFlag;
            continue yyLoop;
          }
          if ((yyN = yyRindex[yyState]) != 0 && (yyN += yyToken) >= 0
              && yyN < yyTable.length && yyCheck[yyN] == yyToken)
            yyN = yyTable[yyN];			// reduce (yyN)
          else
            switch (yyErrorFlag) {
  
            case 0:
              yyerror("syntax error", yyExpecting(yyState), yyNames[yyToken]);
              if (yydebug != null) yydebug.error("syntax error");
  
            case 1: case 2:
              yyErrorFlag = 3;
              do {
                if ((yyN = yySindex[yyStates[yyTop]]) != 0
                    && (yyN += yyErrorCode) >= 0 && yyN < yyTable.length
                    && yyCheck[yyN] == yyErrorCode) {
                  if (yydebug != null)
                    yydebug.shift(yyStates[yyTop], yyTable[yyN], 3);
                  yyState = yyTable[yyN];
                  yyVal = yyLex.value();
                  continue yyLoop;
                }
                if (yydebug != null) yydebug.pop(yyStates[yyTop]);
              } while (-- yyTop >= 0);
              if (yydebug != null) yydebug.reject();
              throw new yyException("irrecoverable syntax error");
  
            case 3:
              if (yyToken == 0) {
                if (yydebug != null) yydebug.reject();
                throw new yyException("irrecoverable syntax error at end-of-file");
              }
              if (yydebug != null)
                yydebug.discard(yyState, yyToken, yyName(yyToken),
  							yyLex.value());
              yyToken = -1;
              continue yyDiscarded;		// leave stack alone
            }
        }
        int yyV = yyTop + 1-yyLen[yyN];
        if (yydebug != null)
          yydebug.reduce(yyState, yyStates[yyV-1], yyN, yyRule[yyN], yyLen[yyN]);
        yyVal = yyDefault(yyV > yyTop ? null : yyVals[yyV]);
        switch (yyN) {
case 1: yyVal = case1_line279(yyVal, yyVals, yyTop); // line 279
break;
case 2: yyVal = case2_line282(yyVal, yyVals, yyTop); // line 282
break;
case 3: yyVal = case3_line295(yyVal, yyVals, yyTop); // line 295
break;
case 4: yyVal = case4_line312(yyVal, yyVals, yyTop); // line 312
break;
case 6: yyVal = case6_line320(yyVal, yyVals, yyTop); // line 320
break;
case 7: yyVal = case7_line323(yyVal, yyVals, yyTop); // line 323
break;
case 8: yyVal = case8_line326(yyVal, yyVals, yyTop); // line 326
break;
case 9: yyVal = case9_line330(yyVal, yyVals, yyTop); // line 330
break;
case 10: yyVal = case10_line332(yyVal, yyVals, yyTop); // line 332
break;
case 11: yyVal = case11_line335(yyVal, yyVals, yyTop); // line 335
break;
case 12: yyVal = case12_line338(yyVal, yyVals, yyTop); // line 338
break;
case 13: yyVal = case13_line341(yyVal, yyVals, yyTop); // line 341
break;
case 14: yyVal = case14_line344(yyVal, yyVals, yyTop); // line 344
break;
case 15: yyVal = case15_line347(yyVal, yyVals, yyTop); // line 347
break;
case 16: yyVal = case16_line350(yyVal, yyVals, yyTop); // line 350
break;
case 17: yyVal = case17_line353(yyVal, yyVals, yyTop); // line 353
break;
case 18: yyVal = case18_line360(yyVal, yyVals, yyTop); // line 360
break;
case 19: yyVal = case19_line367(yyVal, yyVals, yyTop); // line 367
break;
case 20: yyVal = case20_line371(yyVal, yyVals, yyTop); // line 371
break;
case 21: yyVal = case21_line376(yyVal, yyVals, yyTop); // line 376
break;
case 22: yyVal = case22_line381(yyVal, yyVals, yyTop); // line 381
break;
case 23: yyVal = case23_line387(yyVal, yyVals, yyTop); // line 387
break;
case 24: yyVal = case24_line391(yyVal, yyVals, yyTop); // line 391
break;
case 25: yyVal = case25_line396(yyVal, yyVals, yyTop); // line 396
break;
case 26: yyVal = case26_line412(yyVal, yyVals, yyTop); // line 412
break;
case 27: yyVal = case27_line416(yyVal, yyVals, yyTop); // line 416
break;
case 28: yyVal = case28_line419(yyVal, yyVals, yyTop); // line 419
break;
case 29: yyVal = case29_line422(yyVal, yyVals, yyTop); // line 422
break;
case 30: yyVal = case30_line425(yyVal, yyVals, yyTop); // line 425
break;
case 31: yyVal = case31_line428(yyVal, yyVals, yyTop); // line 428
break;
case 32: yyVal = case32_line431(yyVal, yyVals, yyTop); // line 431
break;
case 33: yyVal = case33_line435(yyVal, yyVals, yyTop); // line 435
break;
case 36: yyVal = case36_line444(yyVal, yyVals, yyTop); // line 444
break;
case 37: yyVal = case37_line447(yyVal, yyVals, yyTop); // line 447
break;
case 38: yyVal = case38_line450(yyVal, yyVals, yyTop); // line 450
break;
case 39: yyVal = case39_line453(yyVal, yyVals, yyTop); // line 453
break;
case 41: yyVal = case41_line458(yyVal, yyVals, yyTop); // line 458
break;
case 44: yyVal = case44_line465(yyVal, yyVals, yyTop); // line 465
break;
case 45: yyVal = case45_line468(yyVal, yyVals, yyTop); // line 468
break;
case 46: yyVal = case46_line471(yyVal, yyVals, yyTop); // line 471
break;
case 48: yyVal = case48_line477(yyVal, yyVals, yyTop); // line 477
break;
case 49: yyVal = case49_line480(yyVal, yyVals, yyTop); // line 480
break;
case 50: yyVal = case50_line485(yyVal, yyVals, yyTop); // line 485
break;
case 51: yyVal = case51_line487(yyVal, yyVals, yyTop); // line 487
break;
case 52: yyVal = case52_line493(yyVal, yyVals, yyTop); // line 493
break;
case 53: yyVal = case53_line496(yyVal, yyVals, yyTop); // line 496
break;
case 54: yyVal = case54_line499(yyVal, yyVals, yyTop); // line 499
break;
case 55: yyVal = case55_line502(yyVal, yyVals, yyTop); // line 502
break;
case 56: yyVal = case56_line505(yyVal, yyVals, yyTop); // line 505
break;
case 57: yyVal = case57_line508(yyVal, yyVals, yyTop); // line 508
break;
case 58: yyVal = case58_line511(yyVal, yyVals, yyTop); // line 511
break;
case 59: yyVal = case59_line514(yyVal, yyVals, yyTop); // line 514
break;
case 61: yyVal = case61_line520(yyVal, yyVals, yyTop); // line 520
break;
case 62: yyVal = case62_line525(yyVal, yyVals, yyTop); // line 525
break;
case 63: yyVal = case63_line528(yyVal, yyVals, yyTop); // line 528
break;
case 64: yyVal = case64_line533(yyVal, yyVals, yyTop); // line 533
break;
case 65: yyVal = case65_line536(yyVal, yyVals, yyTop); // line 536
break;
case 66: yyVal = case66_line539(yyVal, yyVals, yyTop); // line 539
break;
case 67: yyVal = case67_line542(yyVal, yyVals, yyTop); // line 542
break;
case 68: yyVal = case68_line545(yyVal, yyVals, yyTop); // line 545
break;
case 69: yyVal = case69_line548(yyVal, yyVals, yyTop); // line 548
break;
case 70: yyVal = case70_line551(yyVal, yyVals, yyTop); // line 551
break;
case 71: yyVal = case71_line554(yyVal, yyVals, yyTop); // line 554
break;
case 72: yyVal = case72_line557(yyVal, yyVals, yyTop); // line 557
break;
case 73: yyVal = case73_line560(yyVal, yyVals, yyTop); // line 560
break;
case 75: yyVal = case75_line565(yyVal, yyVals, yyTop); // line 565
break;
case 76: yyVal = case76_line570(yyVal, yyVals, yyTop); // line 570
break;
case 77: yyVal = case77_line573(yyVal, yyVals, yyTop); // line 573
break;
case 78: yyVal = case78_line578(yyVal, yyVals, yyTop); // line 578
break;
case 79: yyVal = case79_line581(yyVal, yyVals, yyTop); // line 581
break;
case 80: yyVal = case80_line585(yyVal, yyVals, yyTop); // line 585
break;
case 81: yyVal = case81_line588(yyVal, yyVals, yyTop); // line 588
break;
case 82: yyVal = case82_line591(yyVal, yyVals, yyTop); // line 591
break;
case 83: yyVal = case83_line594(yyVal, yyVals, yyTop); // line 594
break;
case 84: yyVal = case84_line597(yyVal, yyVals, yyTop); // line 597
break;
case 85: yyVal = case85_line600(yyVal, yyVals, yyTop); // line 600
break;
case 86: yyVal = case86_line609(yyVal, yyVals, yyTop); // line 609
break;
case 87: yyVal = case87_line618(yyVal, yyVals, yyTop); // line 618
break;
case 88: yyVal = case88_line622(yyVal, yyVals, yyTop); // line 622
break;
case 89: yyVal = case89_line626(yyVal, yyVals, yyTop); // line 626
break;
case 90: yyVal = case90_line629(yyVal, yyVals, yyTop); // line 629
break;
case 91: yyVal = case91_line632(yyVal, yyVals, yyTop); // line 632
break;
case 92: yyVal = case92_line635(yyVal, yyVals, yyTop); // line 635
break;
case 93: yyVal = case93_line638(yyVal, yyVals, yyTop); // line 638
break;
case 94: yyVal = case94_line647(yyVal, yyVals, yyTop); // line 647
break;
case 95: yyVal = case95_line656(yyVal, yyVals, yyTop); // line 656
break;
case 96: yyVal = case96_line660(yyVal, yyVals, yyTop); // line 660
break;
case 98: yyVal = case98_line665(yyVal, yyVals, yyTop); // line 665
break;
case 99: yyVal = case99_line668(yyVal, yyVals, yyTop); // line 668
break;
case 100: yyVal = case100_line671(yyVal, yyVals, yyTop); // line 671
break;
case 104: yyVal = case104_line677(yyVal, yyVals, yyTop); // line 677
break;
case 105: yyVal = case105_line681(yyVal, yyVals, yyTop); // line 681
break;
case 106: yyVal = case106_line687(yyVal, yyVals, yyTop); // line 687
break;
case 107: yyVal = case107_line690(yyVal, yyVals, yyTop); // line 690
break;
case 108: yyVal = case108_line695(yyVal, yyVals, yyTop); // line 695
break;
case 109: yyVal = case109_line698(yyVal, yyVals, yyTop); // line 698
break;
case 110: yyVal = case110_line702(yyVal, yyVals, yyTop); // line 702
break;
case 111: yyVal = case111_line705(yyVal, yyVals, yyTop); // line 705
break;
case 112: yyVal = case112_line707(yyVal, yyVals, yyTop); // line 707
break;
case 184: yyVal = case184_line726(yyVal, yyVals, yyTop); // line 726
break;
case 185: yyVal = case185_line731(yyVal, yyVals, yyTop); // line 731
break;
case 186: yyVal = case186_line736(yyVal, yyVals, yyTop); // line 736
break;
case 187: yyVal = case187_line752(yyVal, yyVals, yyTop); // line 752
break;
case 188: yyVal = case188_line771(yyVal, yyVals, yyTop); // line 771
break;
case 189: yyVal = case189_line775(yyVal, yyVals, yyTop); // line 775
break;
case 190: yyVal = case190_line778(yyVal, yyVals, yyTop); // line 778
break;
case 191: yyVal = case191_line781(yyVal, yyVals, yyTop); // line 781
break;
case 192: yyVal = case192_line784(yyVal, yyVals, yyTop); // line 784
break;
case 193: yyVal = case193_line787(yyVal, yyVals, yyTop); // line 787
break;
case 194: yyVal = case194_line790(yyVal, yyVals, yyTop); // line 790
break;
case 195: yyVal = case195_line793(yyVal, yyVals, yyTop); // line 793
break;
case 196: yyVal = case196_line800(yyVal, yyVals, yyTop); // line 800
break;
case 197: yyVal = case197_line807(yyVal, yyVals, yyTop); // line 807
break;
case 198: yyVal = case198_line810(yyVal, yyVals, yyTop); // line 810
break;
case 199: yyVal = case199_line813(yyVal, yyVals, yyTop); // line 813
break;
case 200: yyVal = case200_line816(yyVal, yyVals, yyTop); // line 816
break;
case 201: yyVal = case201_line819(yyVal, yyVals, yyTop); // line 819
break;
case 202: yyVal = case202_line822(yyVal, yyVals, yyTop); // line 822
break;
case 203: yyVal = case203_line825(yyVal, yyVals, yyTop); // line 825
break;
case 204: yyVal = case204_line828(yyVal, yyVals, yyTop); // line 828
break;
case 205: yyVal = case205_line831(yyVal, yyVals, yyTop); // line 831
break;
case 206: yyVal = case206_line838(yyVal, yyVals, yyTop); // line 838
break;
case 207: yyVal = case207_line841(yyVal, yyVals, yyTop); // line 841
break;
case 208: yyVal = case208_line844(yyVal, yyVals, yyTop); // line 844
break;
case 209: yyVal = case209_line847(yyVal, yyVals, yyTop); // line 847
break;
case 210: yyVal = case210_line850(yyVal, yyVals, yyTop); // line 850
break;
case 211: yyVal = case211_line853(yyVal, yyVals, yyTop); // line 853
break;
case 212: yyVal = case212_line856(yyVal, yyVals, yyTop); // line 856
break;
case 213: yyVal = case213_line859(yyVal, yyVals, yyTop); // line 859
break;
case 214: yyVal = case214_line862(yyVal, yyVals, yyTop); // line 862
break;
case 215: yyVal = case215_line865(yyVal, yyVals, yyTop); // line 865
break;
case 216: yyVal = case216_line868(yyVal, yyVals, yyTop); // line 868
break;
case 217: yyVal = case217_line871(yyVal, yyVals, yyTop); // line 871
break;
case 218: yyVal = case218_line874(yyVal, yyVals, yyTop); // line 874
break;
case 219: yyVal = case219_line883(yyVal, yyVals, yyTop); // line 883
break;
case 220: yyVal = case220_line886(yyVal, yyVals, yyTop); // line 886
break;
case 221: yyVal = case221_line889(yyVal, yyVals, yyTop); // line 889
break;
case 222: yyVal = case222_line892(yyVal, yyVals, yyTop); // line 892
break;
case 223: yyVal = case223_line895(yyVal, yyVals, yyTop); // line 895
break;
case 224: yyVal = case224_line898(yyVal, yyVals, yyTop); // line 898
break;
case 225: yyVal = case225_line901(yyVal, yyVals, yyTop); // line 901
break;
case 226: yyVal = case226_line904(yyVal, yyVals, yyTop); // line 904
break;
case 227: yyVal = case227_line908(yyVal, yyVals, yyTop); // line 908
break;
case 228: yyVal = case228_line911(yyVal, yyVals, yyTop); // line 911
break;
case 229: yyVal = case229_line915(yyVal, yyVals, yyTop); // line 915
break;
case 231: yyVal = case231_line921(yyVal, yyVals, yyTop); // line 921
break;
case 232: yyVal = case232_line924(yyVal, yyVals, yyTop); // line 924
break;
case 233: yyVal = case233_line927(yyVal, yyVals, yyTop); // line 927
break;
case 234: yyVal = case234_line931(yyVal, yyVals, yyTop); // line 931
break;
case 239: yyVal = case239_line940(yyVal, yyVals, yyTop); // line 940
break;
case 240: yyVal = case240_line943(yyVal, yyVals, yyTop); // line 943
break;
case 241: yyVal = case241_line946(yyVal, yyVals, yyTop); // line 946
break;
case 242: yyVal = case242_line950(yyVal, yyVals, yyTop); // line 950
break;
case 243: yyVal = case243_line954(yyVal, yyVals, yyTop); // line 954
break;
case 244: yyVal = case244_line957(yyVal, yyVals, yyTop); // line 957
break;
case 245: yyVal = case245_line959(yyVal, yyVals, yyTop); // line 959
break;
case 246: yyVal = case246_line964(yyVal, yyVals, yyTop); // line 964
break;
case 247: yyVal = case247_line968(yyVal, yyVals, yyTop); // line 968
break;
case 249: yyVal = case249_line973(yyVal, yyVals, yyTop); // line 973
break;
case 250: yyVal = case250_line976(yyVal, yyVals, yyTop); // line 976
break;
case 251: yyVal = case251_line979(yyVal, yyVals, yyTop); // line 979
break;
case 252: yyVal = case252_line988(yyVal, yyVals, yyTop); // line 988
break;
case 253: yyVal = case253_line1000(yyVal, yyVals, yyTop); // line 1000
break;
case 254: yyVal = case254_line1009(yyVal, yyVals, yyTop); // line 1009
break;
case 255: yyVal = case255_line1019(yyVal, yyVals, yyTop); // line 1019
break;
case 264: yyVal = case264_line1031(yyVal, yyVals, yyTop); // line 1031
break;
case 265: yyVal = case265_line1034(yyVal, yyVals, yyTop); // line 1034
break;
case 266: yyVal = case266_line1037(yyVal, yyVals, yyTop); // line 1037
break;
case 267: yyVal = case267_line1039(yyVal, yyVals, yyTop); // line 1039
break;
case 268: yyVal = case268_line1043(yyVal, yyVals, yyTop); // line 1043
break;
case 269: yyVal = case269_line1050(yyVal, yyVals, yyTop); // line 1050
break;
case 270: yyVal = case270_line1053(yyVal, yyVals, yyTop); // line 1053
break;
case 271: yyVal = case271_line1056(yyVal, yyVals, yyTop); // line 1056
break;
case 272: yyVal = case272_line1065(yyVal, yyVals, yyTop); // line 1065
break;
case 273: yyVal = case273_line1068(yyVal, yyVals, yyTop); // line 1068
break;
case 274: yyVal = case274_line1071(yyVal, yyVals, yyTop); // line 1071
break;
case 275: yyVal = case275_line1074(yyVal, yyVals, yyTop); // line 1074
break;
case 276: yyVal = case276_line1077(yyVal, yyVals, yyTop); // line 1077
break;
case 277: yyVal = case277_line1080(yyVal, yyVals, yyTop); // line 1080
break;
case 278: yyVal = case278_line1083(yyVal, yyVals, yyTop); // line 1083
break;
case 279: yyVal = case279_line1086(yyVal, yyVals, yyTop); // line 1086
break;
case 280: yyVal = case280_line1089(yyVal, yyVals, yyTop); // line 1089
break;
case 282: yyVal = case282_line1093(yyVal, yyVals, yyTop); // line 1093
break;
case 283: yyVal = case283_line1101(yyVal, yyVals, yyTop); // line 1101
break;
case 284: yyVal = case284_line1104(yyVal, yyVals, yyTop); // line 1104
break;
case 285: yyVal = case285_line1107(yyVal, yyVals, yyTop); // line 1107
break;
case 286: yyVal = case286_line1110(yyVal, yyVals, yyTop); // line 1110
break;
case 287: yyVal = case287_line1112(yyVal, yyVals, yyTop); // line 1112
break;
case 288: yyVal = case288_line1114(yyVal, yyVals, yyTop); // line 1114
break;
case 289: yyVal = case289_line1118(yyVal, yyVals, yyTop); // line 1118
break;
case 290: yyVal = case290_line1120(yyVal, yyVals, yyTop); // line 1120
break;
case 291: yyVal = case291_line1122(yyVal, yyVals, yyTop); // line 1122
break;
case 292: yyVal = case292_line1126(yyVal, yyVals, yyTop); // line 1126
break;
case 293: yyVal = case293_line1129(yyVal, yyVals, yyTop); // line 1129
break;
case 294: yyVal = case294_line1132(yyVal, yyVals, yyTop); // line 1132
break;
case 295: yyVal = case295_line1134(yyVal, yyVals, yyTop); // line 1134
break;
case 296: yyVal = case296_line1136(yyVal, yyVals, yyTop); // line 1136
break;
case 297: yyVal = case297_line1140(yyVal, yyVals, yyTop); // line 1140
break;
case 298: yyVal = case298_line1145(yyVal, yyVals, yyTop); // line 1145
break;
case 299: yyVal = case299_line1151(yyVal, yyVals, yyTop); // line 1151
break;
case 300: yyVal = case300_line1154(yyVal, yyVals, yyTop); // line 1154
break;
case 301: yyVal = case301_line1158(yyVal, yyVals, yyTop); // line 1158
break;
case 302: yyVal = case302_line1164(yyVal, yyVals, yyTop); // line 1164
break;
case 303: yyVal = case303_line1169(yyVal, yyVals, yyTop); // line 1169
break;
case 304: yyVal = case304_line1175(yyVal, yyVals, yyTop); // line 1175
break;
case 305: yyVal = case305_line1178(yyVal, yyVals, yyTop); // line 1178
break;
case 306: yyVal = case306_line1186(yyVal, yyVals, yyTop); // line 1186
break;
case 307: yyVal = case307_line1188(yyVal, yyVals, yyTop); // line 1188
break;
case 308: yyVal = case308_line1192(yyVal, yyVals, yyTop); // line 1192
break;
case 309: yyVal = case309_line1200(yyVal, yyVals, yyTop); // line 1200
break;
case 310: yyVal = case310_line1203(yyVal, yyVals, yyTop); // line 1203
break;
case 311: yyVal = case311_line1206(yyVal, yyVals, yyTop); // line 1206
break;
case 312: yyVal = case312_line1209(yyVal, yyVals, yyTop); // line 1209
break;
case 313: yyVal = case313_line1213(yyVal, yyVals, yyTop); // line 1213
break;
case 320: yyVal = case320_line1227(yyVal, yyVals, yyTop); // line 1227
break;
case 322: yyVal = case322_line1232(yyVal, yyVals, yyTop); // line 1232
break;
case 324: yyVal = case324_line1237(yyVal, yyVals, yyTop); // line 1237
break;
case 325: yyVal = case325_line1240(yyVal, yyVals, yyTop); // line 1240
break;
case 326: yyVal = case326_line1243(yyVal, yyVals, yyTop); // line 1243
break;
case 327: yyVal = case327_line1247(yyVal, yyVals, yyTop); // line 1247
break;
case 328: yyVal = case328_line1250(yyVal, yyVals, yyTop); // line 1250
break;
case 329: yyVal = case329_line1254(yyVal, yyVals, yyTop); // line 1254
break;
case 330: yyVal = case330_line1257(yyVal, yyVals, yyTop); // line 1257
break;
case 331: yyVal = case331_line1260(yyVal, yyVals, yyTop); // line 1260
break;
case 332: yyVal = case332_line1263(yyVal, yyVals, yyTop); // line 1263
break;
case 333: yyVal = case333_line1266(yyVal, yyVals, yyTop); // line 1266
break;
case 334: yyVal = case334_line1269(yyVal, yyVals, yyTop); // line 1269
break;
case 335: yyVal = case335_line1272(yyVal, yyVals, yyTop); // line 1272
break;
case 336: yyVal = case336_line1275(yyVal, yyVals, yyTop); // line 1275
break;
case 337: yyVal = case337_line1278(yyVal, yyVals, yyTop); // line 1278
break;
case 338: yyVal = case338_line1282(yyVal, yyVals, yyTop); // line 1282
break;
case 339: yyVal = case339_line1285(yyVal, yyVals, yyTop); // line 1285
break;
case 340: yyVal = case340_line1288(yyVal, yyVals, yyTop); // line 1288
break;
case 341: yyVal = case341_line1291(yyVal, yyVals, yyTop); // line 1291
break;
case 342: yyVal = case342_line1294(yyVal, yyVals, yyTop); // line 1294
break;
case 343: yyVal = case343_line1297(yyVal, yyVals, yyTop); // line 1297
break;
case 344: yyVal = case344_line1301(yyVal, yyVals, yyTop); // line 1301
break;
case 345: yyVal = case345_line1304(yyVal, yyVals, yyTop); // line 1304
break;
case 346: yyVal = case346_line1307(yyVal, yyVals, yyTop); // line 1307
break;
case 347: yyVal = case347_line1310(yyVal, yyVals, yyTop); // line 1310
break;
case 348: yyVal = case348_line1313(yyVal, yyVals, yyTop); // line 1313
break;
case 349: yyVal = case349_line1316(yyVal, yyVals, yyTop); // line 1316
break;
case 350: yyVal = case350_line1319(yyVal, yyVals, yyTop); // line 1319
break;
case 351: yyVal = case351_line1322(yyVal, yyVals, yyTop); // line 1322
break;
case 352: yyVal = case352_line1325(yyVal, yyVals, yyTop); // line 1325
break;
case 353: yyVal = case353_line1329(yyVal, yyVals, yyTop); // line 1329
break;
case 354: yyVal = case354_line1333(yyVal, yyVals, yyTop); // line 1333
break;
case 355: yyVal = case355_line1338(yyVal, yyVals, yyTop); // line 1338
break;
case 356: yyVal = case356_line1341(yyVal, yyVals, yyTop); // line 1341
break;
case 357: yyVal = case357_line1344(yyVal, yyVals, yyTop); // line 1344
break;
case 359: yyVal = case359_line1350(yyVal, yyVals, yyTop); // line 1350
break;
case 360: yyVal = case360_line1355(yyVal, yyVals, yyTop); // line 1355
break;
case 361: yyVal = case361_line1358(yyVal, yyVals, yyTop); // line 1358
break;
case 362: yyVal = case362_line1362(yyVal, yyVals, yyTop); // line 1362
break;
case 363: yyVal = case363_line1365(yyVal, yyVals, yyTop); // line 1365
break;
case 364: yyVal = case364_line1369(yyVal, yyVals, yyTop); // line 1369
break;
case 365: yyVal = case365_line1373(yyVal, yyVals, yyTop); // line 1373
break;
case 366: yyVal = case366_line1379(yyVal, yyVals, yyTop); // line 1379
break;
case 367: yyVal = case367_line1383(yyVal, yyVals, yyTop); // line 1383
break;
case 368: yyVal = case368_line1388(yyVal, yyVals, yyTop); // line 1388
break;
case 369: yyVal = case369_line1391(yyVal, yyVals, yyTop); // line 1391
break;
case 370: yyVal = case370_line1395(yyVal, yyVals, yyTop); // line 1395
break;
case 371: yyVal = case371_line1397(yyVal, yyVals, yyTop); // line 1397
break;
case 372: yyVal = case372_line1402(yyVal, yyVals, yyTop); // line 1402
break;
case 373: yyVal = case373_line1413(yyVal, yyVals, yyTop); // line 1413
break;
case 374: yyVal = case374_line1416(yyVal, yyVals, yyTop); // line 1416
break;
case 375: yyVal = case375_line1420(yyVal, yyVals, yyTop); // line 1420
break;
case 376: yyVal = case376_line1423(yyVal, yyVals, yyTop); // line 1423
break;
case 377: yyVal = case377_line1426(yyVal, yyVals, yyTop); // line 1426
break;
case 378: yyVal = case378_line1429(yyVal, yyVals, yyTop); // line 1429
break;
case 379: yyVal = case379_line1432(yyVal, yyVals, yyTop); // line 1432
break;
case 380: yyVal = case380_line1435(yyVal, yyVals, yyTop); // line 1435
break;
case 381: yyVal = case381_line1438(yyVal, yyVals, yyTop); // line 1438
break;
case 382: yyVal = case382_line1441(yyVal, yyVals, yyTop); // line 1441
break;
case 383: yyVal = case383_line1444(yyVal, yyVals, yyTop); // line 1444
break;
case 384: yyVal = case384_line1452(yyVal, yyVals, yyTop); // line 1452
break;
case 385: yyVal = case385_line1454(yyVal, yyVals, yyTop); // line 1454
break;
case 386: yyVal = case386_line1458(yyVal, yyVals, yyTop); // line 1458
break;
case 387: yyVal = case387_line1460(yyVal, yyVals, yyTop); // line 1460
break;
case 388: yyVal = case388_line1466(yyVal, yyVals, yyTop); // line 1466
break;
case 391: yyVal = case391_line1472(yyVal, yyVals, yyTop); // line 1472
break;
case 392: yyVal = case392_line1485(yyVal, yyVals, yyTop); // line 1485
break;
case 393: yyVal = case393_line1489(yyVal, yyVals, yyTop); // line 1489
break;
case 394: yyVal = case394_line1492(yyVal, yyVals, yyTop); // line 1492
break;
case 396: yyVal = case396_line1498(yyVal, yyVals, yyTop); // line 1498
break;
case 398: yyVal = case398_line1503(yyVal, yyVals, yyTop); // line 1503
break;
case 401: yyVal = case401_line1509(yyVal, yyVals, yyTop); // line 1509
break;
case 403: yyVal = case403_line1515(yyVal, yyVals, yyTop); // line 1515
break;
case 404: yyVal = case404_line1528(yyVal, yyVals, yyTop); // line 1528
break;
case 405: yyVal = case405_line1531(yyVal, yyVals, yyTop); // line 1531
break;
case 406: yyVal = case406_line1534(yyVal, yyVals, yyTop); // line 1534
break;
case 407: yyVal = case407_line1538(yyVal, yyVals, yyTop); // line 1538
break;
case 408: yyVal = case408_line1553(yyVal, yyVals, yyTop); // line 1553
break;
case 409: yyVal = case409_line1569(yyVal, yyVals, yyTop); // line 1569
break;
case 410: yyVal = case410_line1584(yyVal, yyVals, yyTop); // line 1584
break;
case 411: yyVal = case411_line1587(yyVal, yyVals, yyTop); // line 1587
break;
case 412: yyVal = case412_line1591(yyVal, yyVals, yyTop); // line 1591
break;
case 413: yyVal = case413_line1594(yyVal, yyVals, yyTop); // line 1594
break;
case 415: yyVal = case415_line1599(yyVal, yyVals, yyTop); // line 1599
break;
case 416: yyVal = case416_line1603(yyVal, yyVals, yyTop); // line 1603
break;
case 417: yyVal = case417_line1606(yyVal, yyVals, yyTop); // line 1606
break;
case 418: yyVal = case418_line1611(yyVal, yyVals, yyTop); // line 1611
break;
case 419: yyVal = case419_line1614(yyVal, yyVals, yyTop); // line 1614
break;
case 420: yyVal = case420_line1618(yyVal, yyVals, yyTop); // line 1618
break;
case 421: yyVal = case421_line1621(yyVal, yyVals, yyTop); // line 1621
break;
case 422: yyVal = case422_line1625(yyVal, yyVals, yyTop); // line 1625
break;
case 423: yyVal = case423_line1628(yyVal, yyVals, yyTop); // line 1628
break;
case 424: yyVal = case424_line1632(yyVal, yyVals, yyTop); // line 1632
break;
case 425: yyVal = case425_line1635(yyVal, yyVals, yyTop); // line 1635
break;
case 426: yyVal = case426_line1639(yyVal, yyVals, yyTop); // line 1639
break;
case 427: yyVal = case427_line1643(yyVal, yyVals, yyTop); // line 1643
break;
case 428: yyVal = case428_line1647(yyVal, yyVals, yyTop); // line 1647
break;
case 429: yyVal = case429_line1653(yyVal, yyVals, yyTop); // line 1653
break;
case 430: yyVal = case430_line1656(yyVal, yyVals, yyTop); // line 1656
break;
case 431: yyVal = case431_line1659(yyVal, yyVals, yyTop); // line 1659
break;
case 433: yyVal = case433_line1665(yyVal, yyVals, yyTop); // line 1665
break;
case 438: yyVal = case438_line1674(yyVal, yyVals, yyTop); // line 1674
break;
case 439: yyVal = case439_line1698(yyVal, yyVals, yyTop); // line 1698
break;
case 440: yyVal = case440_line1701(yyVal, yyVals, yyTop); // line 1701
break;
case 441: yyVal = case441_line1704(yyVal, yyVals, yyTop); // line 1704
break;
case 442: yyVal = case442_line1707(yyVal, yyVals, yyTop); // line 1707
break;
case 448: yyVal = case448_line1712(yyVal, yyVals, yyTop); // line 1712
break;
case 449: yyVal = case449_line1715(yyVal, yyVals, yyTop); // line 1715
break;
case 450: yyVal = case450_line1718(yyVal, yyVals, yyTop); // line 1718
break;
case 451: yyVal = case451_line1721(yyVal, yyVals, yyTop); // line 1721
break;
case 452: yyVal = case452_line1724(yyVal, yyVals, yyTop); // line 1724
break;
case 453: yyVal = case453_line1727(yyVal, yyVals, yyTop); // line 1727
break;
case 454: yyVal = case454_line1730(yyVal, yyVals, yyTop); // line 1730
break;
case 455: yyVal = case455_line1734(yyVal, yyVals, yyTop); // line 1734
break;
case 456: yyVal = case456_line1738(yyVal, yyVals, yyTop); // line 1738
break;
case 457: yyVal = case457_line1742(yyVal, yyVals, yyTop); // line 1742
break;
case 458: yyVal = case458_line1745(yyVal, yyVals, yyTop); // line 1745
break;
case 459: yyVal = case459_line1749(yyVal, yyVals, yyTop); // line 1749
break;
case 460: yyVal = case460_line1752(yyVal, yyVals, yyTop); // line 1752
break;
case 461: yyVal = case461_line1754(yyVal, yyVals, yyTop); // line 1754
break;
case 462: yyVal = case462_line1757(yyVal, yyVals, yyTop); // line 1757
break;
case 463: yyVal = case463_line1763(yyVal, yyVals, yyTop); // line 1763
break;
case 464: yyVal = case464_line1768(yyVal, yyVals, yyTop); // line 1768
break;
case 465: yyVal = case465_line1772(yyVal, yyVals, yyTop); // line 1772
break;
case 466: yyVal = case466_line1775(yyVal, yyVals, yyTop); // line 1775
break;
case 467: yyVal = case467_line1778(yyVal, yyVals, yyTop); // line 1778
break;
case 468: yyVal = case468_line1781(yyVal, yyVals, yyTop); // line 1781
break;
case 469: yyVal = case469_line1784(yyVal, yyVals, yyTop); // line 1784
break;
case 470: yyVal = case470_line1787(yyVal, yyVals, yyTop); // line 1787
break;
case 471: yyVal = case471_line1790(yyVal, yyVals, yyTop); // line 1790
break;
case 472: yyVal = case472_line1793(yyVal, yyVals, yyTop); // line 1793
break;
case 473: yyVal = case473_line1796(yyVal, yyVals, yyTop); // line 1796
break;
case 474: yyVal = case474_line1799(yyVal, yyVals, yyTop); // line 1799
break;
case 475: yyVal = case475_line1802(yyVal, yyVals, yyTop); // line 1802
break;
case 476: yyVal = case476_line1805(yyVal, yyVals, yyTop); // line 1805
break;
case 477: yyVal = case477_line1808(yyVal, yyVals, yyTop); // line 1808
break;
case 478: yyVal = case478_line1811(yyVal, yyVals, yyTop); // line 1811
break;
case 479: yyVal = case479_line1814(yyVal, yyVals, yyTop); // line 1814
break;
case 480: yyVal = case480_line1818(yyVal, yyVals, yyTop); // line 1818
break;
case 481: yyVal = case481_line1821(yyVal, yyVals, yyTop); // line 1821
break;
case 482: yyVal = case482_line1824(yyVal, yyVals, yyTop); // line 1824
break;
case 483: yyVal = case483_line1827(yyVal, yyVals, yyTop); // line 1827
break;
case 485: yyVal = case485_line1833(yyVal, yyVals, yyTop); // line 1833
break;
case 486: yyVal = case486_line1843(yyVal, yyVals, yyTop); // line 1843
break;
case 487: yyVal = case487_line1850(yyVal, yyVals, yyTop); // line 1850
break;
case 488: yyVal = case488_line1865(yyVal, yyVals, yyTop); // line 1865
break;
case 489: yyVal = case489_line1868(yyVal, yyVals, yyTop); // line 1868
break;
case 490: yyVal = case490_line1873(yyVal, yyVals, yyTop); // line 1873
break;
case 491: yyVal = case491_line1882(yyVal, yyVals, yyTop); // line 1882
break;
case 492: yyVal = case492_line1891(yyVal, yyVals, yyTop); // line 1891
break;
case 493: yyVal = case493_line1894(yyVal, yyVals, yyTop); // line 1894
break;
case 494: yyVal = case494_line1898(yyVal, yyVals, yyTop); // line 1898
break;
case 495: yyVal = case495_line1901(yyVal, yyVals, yyTop); // line 1901
break;
case 498: yyVal = case498_line1907(yyVal, yyVals, yyTop); // line 1907
break;
case 499: yyVal = case499_line1914(yyVal, yyVals, yyTop); // line 1914
break;
case 502: yyVal = case502_line1921(yyVal, yyVals, yyTop); // line 1921
break;
case 503: yyVal = case503_line1931(yyVal, yyVals, yyTop); // line 1931
break;
case 504: yyVal = case504_line1934(yyVal, yyVals, yyTop); // line 1934
break;
case 505: yyVal = case505_line1938(yyVal, yyVals, yyTop); // line 1938
break;
case 506: yyVal = case506_line1944(yyVal, yyVals, yyTop); // line 1944
break;
case 507: yyVal = case507_line1946(yyVal, yyVals, yyTop); // line 1946
break;
case 508: yyVal = case508_line1956(yyVal, yyVals, yyTop); // line 1956
break;
case 509: yyVal = case509_line1959(yyVal, yyVals, yyTop); // line 1959
break;
case 511: yyVal = case511_line1964(yyVal, yyVals, yyTop); // line 1964
break;
case 512: yyVal = case512_line1968(yyVal, yyVals, yyTop); // line 1968
break;
case 513: yyVal = case513_line1978(yyVal, yyVals, yyTop); // line 1978
break;
case 530: yyVal = case530_line1988(yyVal, yyVals, yyTop); // line 1988
break;
case 531: yyVal = case531_line1991(yyVal, yyVals, yyTop); // line 1991
break;
case 535: yyVal = case535_line1996(yyVal, yyVals, yyTop); // line 1996
break;
case 538: yyVal = case538_line2002(yyVal, yyVals, yyTop); // line 2002
break;
case 539: yyVal = case539_line2006(yyVal, yyVals, yyTop); // line 2006
break;
case 540: yyVal = case540_line2010(yyVal, yyVals, yyTop); // line 2010
break;
// ACTIONS_END
        }
        yyTop -= yyLen[yyN];
        yyState = yyStates[yyTop];
        int yyM = yyLhs[yyN];
        if (yyState == 0 && yyM == 0) {
          if (yydebug != null) yydebug.shift(0, yyFinal);
          yyState = yyFinal;
          if (yyToken < 0) {
            yyToken = yyLex.advance() ? yyLex.token() : 0;
            if (yydebug != null)
               yydebug.lex(yyState, yyToken,yyName(yyToken), yyLex.value());
          }
          if (yyToken == 0) {
            if (yydebug != null) yydebug.accept(yyVal);
            return yyVal;
          }
          continue yyLoop;
        }
        if ((yyN = yyGindex[yyM]) != 0 && (yyN += yyState) >= 0
            && yyN < yyTable.length && yyCheck[yyN] == yyState)
          yyState = yyTable[yyN];
        else
          yyState = yyDgoto[yyM];
        if (yydebug != null) yydebug.shift(yyStates[yyTop], yyState);
        continue yyLoop;
      }
    }
  }

public Object case481_line1821(Object yyVal, Object[] yyVals, int yyTop) {
                    yyerror("formal argument cannot be an instance variable");
    return yyVal;
}
public Object case449_line1715(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new Token("self", Tokens.kSELF, ((Token)yyVals[0+yyTop]).getPosition());
    return yyVal;
}
public Object case16_line350(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IfNode(getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), null, ((Node)yyVals[-2+yyTop]));
    return yyVal;
}
public Object case196_line800(Object yyVal, Object[] yyVals, int yyTop) {
                    support.checkExpression(((Node)yyVals[-2+yyTop]));
                    support.checkExpression(((Node)yyVals[0+yyTop]));

                    boolean isLiteral = ((Node)yyVals[-2+yyTop]) instanceof FixnumNode && ((Node)yyVals[0+yyTop]) instanceof FixnumNode;
                    yyVal = new DotNode(getPosition(((Node)yyVals[-2+yyTop])), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]), true, isLiteral);
    return yyVal;
}
public Object case265_line1034(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BeginNode(getPosition(((Token)yyVals[-2+yyTop])), ((Node)yyVals[-1+yyTop]) == null ? NilImplicitNode.NIL : ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case456_line1738(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.assignable(((Token)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
}
public Object case233_line927(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(getPosition(((ListNode)yyVals[-1+yyTop])), new HashNode(getPosition(), ((ListNode)yyVals[-1+yyTop])));
    return yyVal;
}
public Object case422_line1625(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
}
public Object case378_line1429(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-2+yyTop]), ((Token)yyVals[0+yyTop]), null, null);
    return yyVal;
}
public Object case366_line1379(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ArgsNode)yyVals[-2+yyTop]);
                    ((ISourcePositionHolder)yyVal).setPosition(getPosition(((Token)yyVals[-3+yyTop])));
    return yyVal;
}
public Object case349_line1316(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-3+yyTop])), null, ((ListNode)yyVals[-3+yyTop]), null, ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case22_line381(Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        warnings.warn(ID.END_IN_METHOD, getPosition(((Token)yyVals[-3+yyTop])), "END in method; use at_exit");
                    }
                    yyVal = new PostExeNode(getPosition(((Node)yyVals[-1+yyTop])), ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case99_line668(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_colon2(((Token)yyVals[0+yyTop]).getPosition(), null, (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case222_line892(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "<<", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case513_line1978(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(getPosition(((Token)yyVals[-1+yyTop])), new SymbolNode(getPosition(((Token)yyVals[-1+yyTop])), (String) ((Token)yyVals[-1+yyTop]).getValue())).add(((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case466_line1775(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-7+yyTop])), ((ListNode)yyVals[-7+yyTop]), ((ListNode)yyVals[-5+yyTop]), ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case382_line1441(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ZSuperNode(((Token)yyVals[0+yyTop]).getPosition());
    return yyVal;
}
public Object case367_line1383(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ArgsNode)yyVals[-1+yyTop]);
                    ((ISourcePositionHolder)yyVal).setPosition(getPosition(((ArgsNode)yyVals[-1+yyTop])));
    return yyVal;
}
public Object case298_line1145(Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = ((Node)yyVals[-1+yyTop]) == null ? NilImplicitNode.NIL : ((Node)yyVals[-1+yyTop]);

                    yyVal = new ClassNode(getPosition(((Token)yyVals[-5+yyTop])), ((Colon3Node)yyVals[-4+yyTop]), support.getCurrentScope(), body, ((Node)yyVals[-3+yyTop]));
                    support.popCurrentScope();
    return yyVal;
}
public Object case264_line1031(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new FCallNoArgNode(((Token)yyVals[0+yyTop]).getPosition(), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case33_line435(Object yyVal, Object[] yyVals, int yyTop) {
                    ((AssignableNode)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                    yyVal = ((MultipleAsgn19Node)yyVals[-2+yyTop]);
                    ((MultipleAsgn19Node)yyVals[-2+yyTop]).setPosition(getPosition(((MultipleAsgn19Node)yyVals[-2+yyTop])));
    return yyVal;
}
public Object case495_line1901(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.appendToBlock(((ListNode)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case431_line1659(Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = new ClassVarNode(((Token)yyVals[0+yyTop]).getPosition(), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case357_line1344(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ArgsNode)yyVals[-2+yyTop]);
    return yyVal;
}
public Object case310_line1203(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new NextNode(((Token)yyVals[0+yyTop]).getPosition(), NilImplicitNode.NIL);
    return yyVal;
}
public Object case27_line416(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new OpAsgnNode(getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((Node)yyVals[0+yyTop]), (String) ((Token)yyVals[-2+yyTop]).getValue(), (String) ((Token)yyVals[-1+yyTop]).getValue());
    return yyVal;
}
public Object case306_line1186(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setState(LexState.EXPR_FNAME);
    return yyVal;
}
public Object case234_line931(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
                    if (yyVal != null) ((Node)yyVal).setPosition(getPosition(((Token)yyVals[-2+yyTop])));
    return yyVal;
}
public Object case14_line344(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case504_line1934(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
}
public Object case417_line1606(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-1+yyTop]);
                    ((ISourcePositionHolder)yyVal).setPosition(getPosition(((Token)yyVals[-2+yyTop])));
    return yyVal;
}
public Object case348_line1313(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-1+yyTop])), null, ((ListNode)yyVals[-1+yyTop]), null, null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case320_line1227(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IfNode(getPosition(((Token)yyVals[-4+yyTop])), support.getConditionNode(((Node)yyVals[-3+yyTop])), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case268_line1043(Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-1+yyTop]) != null) {
                        /* compstmt position includes both parens around it*/
                        ((ISourcePositionHolder) ((Node)yyVals[-1+yyTop])).setPosition(getPosition(((Token)yyVals[-2+yyTop])));
                    }
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case278_line1083(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(support.getConditionNode(((Node)yyVals[-1+yyTop])), "!");
    return yyVal;
}
public Object case18_line360(Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-2+yyTop]) != null && ((Node)yyVals[-2+yyTop]) instanceof BeginNode) {
                        yyVal = new UntilNode(getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), ((BeginNode)yyVals[-2+yyTop]).getBodyNode(), false);
                    } else {
                        yyVal = new UntilNode(getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), ((Node)yyVals[-2+yyTop]), true);
                    }
    return yyVal;
}
public Object case251_line979(Object yyVal, Object[] yyVals, int yyTop) {
                    Node node = support.splat_array(((Node)yyVals[-2+yyTop]));

                    if (node != null) {
                        yyVal = support.list_append(node, ((Node)yyVals[0+yyTop]));
                    } else {
                        yyVal = support.arg_append(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
                    }
    return yyVal;
}
public Object case476_line1805(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((RestArgNode)yyVals[-1+yyTop])), null, null, ((RestArgNode)yyVals[-1+yyTop]), null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case438_line1674(Object yyVal, Object[] yyVals, int yyTop) {
                     lexer.setState(LexState.EXPR_END);

                     /* DStrNode: :"some text #{some expression}"*/
                     /* StrNode: :"some text"*/
                     /* EvStrNode :"#{some expression}"*/
                     if (((Node)yyVals[-1+yyTop]) == null) {
                       yyerror("empty symbol literal");
                     }
                     /* FIXME: No node here seems to be an empty string
                        instead of an error
                        if (!($$ = $2)) {
                        $$ = NEW_LIT(ID2SYM(rb_intern("")));
                        }
                     */

                     if (((Node)yyVals[-1+yyTop]) instanceof DStrNode) {
                         yyVal = new DSymbolNode(getPosition(((Token)yyVals[-2+yyTop])), ((DStrNode)yyVals[-1+yyTop]));
                     } else {
                         yyVal = new DSymbolNode(getPosition(((Token)yyVals[-2+yyTop])));
                         ((DSymbolNode)yyVal).add(((Node)yyVals[-1+yyTop]));
                     }
    return yyVal;
}
public Object case426_line1639(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setStrTerm(((StrTerm)yyVals[-1+yyTop]));
                    yyVal = new EvStrNode(getPosition(((Token)yyVals[-2+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case220_line886(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new NotNode(getPosition(((Token)yyVals[-1+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])));
    return yyVal;
}
public Object case499_line1914(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new UnnamedRestArgNode(((Token)yyVals[0+yyTop]).getPosition(), support.getCurrentScope().getLocalScope().addVariable("*"));
    return yyVal;
}
public Object case471_line1790(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-1+yyTop])), ((ListNode)yyVals[-1+yyTop]), null, null, null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case427_line1643(Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = lexer.getStrTerm();
                   lexer.setStrTerm(null);
                   lexer.setState(LexState.EXPR_BEG);
    return yyVal;
}
public Object case364_line1369(Object yyVal, Object[] yyVals, int yyTop) {
                    support.pushBlockScope();
                    yyVal = lexer.getLeftParenBegin();
                    lexer.setLeftParenBegin(lexer.incrementParenNest());
    return yyVal;
}
public Object case59_line514(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_yield(getPosition(((Token)yyVals[-1+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case254_line1009(Object yyVal, Object[] yyVals, int yyTop) {
                    Node node = null;

                    if (((Node)yyVals[0+yyTop]) instanceof ArrayNode &&
                        (node = support.splat_array(((Node)yyVals[-3+yyTop]))) != null) {
                        yyVal = support.list_concat(node, ((Node)yyVals[0+yyTop]));
                    } else {
                        yyVal = support.arg_concat(getPosition(((Node)yyVals[-3+yyTop])), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]));
                    }
    return yyVal;
}
public Object case244_line957(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new Long(lexer.getCmdArgumentState().begin());
    return yyVal;
}
public Object case280_line1089(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new FCallNoArgBlockNode(getPosition(((Token)yyVals[-1+yyTop])), (String) ((Token)yyVals[-1+yyTop]).getValue(), ((IterNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case255_line1019(Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = support.newSplatNode(getPosition(((Token)yyVals[-1+yyTop])), ((Node)yyVals[0+yyTop]));  
    return yyVal;
}
public Object case45_line468(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BreakNode(getPosition(((Token)yyVals[-1+yyTop])), support.ret_args(((Node)yyVals[0+yyTop]), getPosition(((Token)yyVals[-1+yyTop]))));
    return yyVal;
}
public Object case218_line874(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getMatchNode(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
                  /* ENEBO
                        $$ = match_op($1, $3);
                        if (nd_type($1) == NODE_LIT && TYPE($1->nd_lit) == T_REGEXP) {
                            $$ = reg_named_capture_assign($1->nd_lit, $$);
                        }
                  */
    return yyVal;
}
public Object case491_line1882(Object yyVal, Object[] yyVals, int yyTop) {
                    if (!support.is_local_id(((Token)yyVals[-2+yyTop]))) {
                        yyerror("formal argument must be local variable");
                    }
                    support.shadowing_lvar(((Token)yyVals[-2+yyTop]));
                    support.arg_var(((Token)yyVals[-2+yyTop]));
                    yyVal = new OptArgNode(getPosition(((Token)yyVals[-2+yyTop])), support.assignable(((Token)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop])));
    return yyVal;
}
public Object case490_line1873(Object yyVal, Object[] yyVals, int yyTop) {
                    if (!support.is_local_id(((Token)yyVals[-2+yyTop]))) {
                        yyerror("formal argument must be local variable");
                    }
                    support.shadowing_lvar(((Token)yyVals[-2+yyTop]));
                    support.arg_var(((Token)yyVals[-2+yyTop]));
                    yyVal = new OptArgNode(getPosition(((Token)yyVals[-2+yyTop])), support.assignable(((Token)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop])));
    return yyVal;
}
public Object case475_line1802(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-3+yyTop])), null, ((ListNode)yyVals[-3+yyTop]), null, ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case330_line1257(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((ListNode)yyVals[-3+yyTop])), ((ListNode)yyVals[-3+yyTop]), support.assignable(((Token)yyVals[0+yyTop]), null), null);
    return yyVal;
}
public Object case193_line787(Object yyVal, Object[] yyVals, int yyTop) {
                    yyerror("constant re-assignment");
    return yyVal;
}
public Object case239_line940(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(getPosition(((Node)yyVals[0+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case184_line726(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.node_assign(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
                    /* FIXME: Consider fixing node_assign itself rather than single case*/
                    ((Node)yyVal).setPosition(getPosition(((Node)yyVals[-2+yyTop])));
    return yyVal;
}
public Object case246_line964(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BlockPassNode(getPosition(((Token)yyVals[-1+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case454_line1730(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new Token("__ENCODING__", Tokens.k__ENCODING__, ((Token)yyVals[0+yyTop]).getPosition());
    return yyVal;
}
public Object case300_line1154(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new Integer(support.getInSingle());
                    support.setInSingle(0);
                    support.pushLocalScope();
    return yyVal;
}
public Object case46_line471(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new NextNode(getPosition(((Token)yyVals[-1+yyTop])), support.ret_args(((Node)yyVals[0+yyTop]), getPosition(((Token)yyVals[-1+yyTop]))));
    return yyVal;
}
public Object case76_line570(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(((Node)yyVals[-1+yyTop]).getPosition(), ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case291_line1122(Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = ((Node)yyVals[-1+yyTop]) == null ? NilImplicitNode.NIL : ((Node)yyVals[-1+yyTop]);
                    yyVal = new UntilNode(getPosition(((Token)yyVals[-6+yyTop])), support.getConditionNode(((Node)yyVals[-4+yyTop])), body);
    return yyVal;
}
public Object case412_line1591(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ArrayNode(getPosition());
    return yyVal;
}
public Object case335_line1272(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((Token)yyVals[-3+yyTop])), null, support.assignable(((Token)yyVals[-2+yyTop]), null), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case334_line1269(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((Token)yyVals[-1+yyTop])), null, support.assignable(((Token)yyVals[0+yyTop]), null), null);
    return yyVal;
}
public Object case194_line790(Object yyVal, Object[] yyVals, int yyTop) {
                    support.backrefAssignError(((Node)yyVals[-2+yyTop]));
    return yyVal;
}
public Object case57_line508(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-4+yyTop]), ((Token)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), ((IterNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case106_line687(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Token)yyVals[0+yyTop]);
    return yyVal;
}
public Object case88_line622(Object yyVal, Object[] yyVals, int yyTop) {
                      /* if (!($$ = assignable($1, 0))) $$ = NEW_BEGIN(0);*/
                    yyVal = support.assignable(((Token)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
}
public Object case493_line1894(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.appendToBlock(((ListNode)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case383_line1444(Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-3+yyTop]) instanceof SelfNode) {
                        yyVal = support.new_fcall(new Token("[]", getPosition(((Node)yyVals[-3+yyTop]))), ((Node)yyVals[-1+yyTop]), null);
                    } else {
                        yyVal = support.new_call(((Node)yyVals[-3+yyTop]), new Token("[]", getPosition(((Node)yyVals[-3+yyTop]))), ((Node)yyVals[-1+yyTop]), null);
                    }
    return yyVal;
}
public Object case216_line868(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "===", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case72_line557(Object yyVal, Object[] yyVals, int yyTop) {
                      yyVal = new MultipleAsgn19Node(getPosition(((Token)yyVals[0+yyTop])), null, new StarNode(getPosition()), null);
    return yyVal;
}
public Object case188_line771(Object yyVal, Object[] yyVals, int yyTop) {
  /* FIXME: arg_concat missing for opt_call_args*/
                    yyVal = support.new_opElementAsgnNode(getPosition(((Node)yyVals[-5+yyTop])), ((Node)yyVals[-5+yyTop]), (String) ((Token)yyVals[-1+yyTop]).getValue(), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case295_line1134(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getConditionState().end();
    return yyVal;
}
public Object case539_line2006(Object yyVal, Object[] yyVals, int yyTop) {
                      yyVal = null;
    return yyVal;
}
public Object case452_line1724(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new Token("__FILE__", Tokens.k__FILE__, ((Token)yyVals[0+yyTop]).getPosition());
    return yyVal;
}
public Object case376_line1423(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((Token)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case107_line690(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Token)yyVals[0+yyTop]);
    return yyVal;
}
public Object case20_line371(Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        yyerror("BEGIN in method");
                    }
                    support.pushLocalScope();
    return yyVal;
}
public Object case252_line988(Object yyVal, Object[] yyVals, int yyTop) {
                    Node node = null;

                    /* FIXME: lose syntactical elements here (and others like this)*/
                    if (((Node)yyVals[0+yyTop]) instanceof ArrayNode &&
                        (node = support.splat_array(((Node)yyVals[-3+yyTop]))) != null) {
                        yyVal = support.list_concat(node, ((Node)yyVals[0+yyTop]));
                    } else {
                        yyVal = support.arg_concat(getPosition(((Node)yyVals[-3+yyTop])), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]));
                    }
    return yyVal;
}
public Object case201_line819(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "%", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case66_line539(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((ListNode)yyVals[-2+yyTop])), ((ListNode)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]), (ListNode) null);
    return yyVal;
}
public Object case284_line1104(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IfNode(getPosition(((Token)yyVals[-5+yyTop])), support.getConditionNode(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case6_line320(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newline_node(((Node)yyVals[0+yyTop]), getPosition(((Node)yyVals[0+yyTop])));
    return yyVal;
}
public Object case200_line816(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "/", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case505_line1938(Object yyVal, Object[] yyVals, int yyTop) {
                    if (!(((Node)yyVals[0+yyTop]) instanceof SelfNode)) {
                        support.checkExpression(((Node)yyVals[0+yyTop]));
                    }
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case209_line847(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "&", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case202_line822(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "**", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case394_line1492(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.splat_array(((Node)yyVals[0+yyTop]));
                    if (yyVal == null) yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case345_line1304(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-1+yyTop])), ((ListNode)yyVals[-1+yyTop]), null, null, null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case94_line647(Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        yyerror("dynamic constant assignment");
                    }

                    ISourcePosition position = getPosition(((Token)yyVals[-1+yyTop]));

                    yyVal = new ConstDeclNode(position, null, support.new_colon3(position, (String) ((Token)yyVals[0+yyTop]).getValue()), NilImplicitNode.NIL);
    return yyVal;
}
public Object case39_line453(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new NotNode(getPosition(((Token)yyVals[-1+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])));
    return yyVal;
}
public Object case219_line883(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new NotNode(getPosition(((Node)yyVals[-2+yyTop])), support.getMatchNode(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop])));
    return yyVal;
}
public Object case9_line330(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setState(LexState.EXPR_FNAME);
    return yyVal;
}
public Object case83_line594(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case362_line1362(Object yyVal, Object[] yyVals, int yyTop) {
                    support.new_bv(((Token)yyVals[0+yyTop]));
    return yyVal;
}
public Object case86_line609(Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        yyerror("dynamic constant assignment");
                    }

                    ISourcePosition position = getPosition(((Token)yyVals[-1+yyTop]));

                    yyVal = new ConstDeclNode(position, null, support.new_colon3(position, (String) ((Token)yyVals[0+yyTop]).getValue()), NilImplicitNode.NIL);
    return yyVal;
}
public Object case324_line1237(Object yyVal, Object[] yyVals, int yyTop) {
    return yyVal;
}
public Object case53_line496(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_fcall(((Token)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), ((IterNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case31_line428(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.node_assign(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case502_line1921(Object yyVal, Object[] yyVals, int yyTop) {
                    String identifier = (String) ((Token)yyVals[0+yyTop]).getValue();

                    if (!support.is_local_id(((Token)yyVals[0+yyTop]))) {
                        yyerror("block argument must be local variable");
                    }
                    support.shadowing_lvar(((Token)yyVals[0+yyTop]));
                    yyVal = new BlockArgNode(getPosition(((Token)yyVals[-1+yyTop])), support.arg_var(((Token)yyVals[0+yyTop])), identifier);
    return yyVal;
}
public Object case473_line1796(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-5+yyTop])), null, ((ListNode)yyVals[-5+yyTop]), ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case457_line1742(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case211_line853(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ">", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case1_line279(Object yyVal, Object[] yyVals, int yyTop) {
                  lexer.setState(LexState.EXPR_BEG);
                  support.initTopLocalVariables();
    return yyVal;
}
public Object case232_line924(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.arg_append(((Node)yyVals[-3+yyTop]), new HashNode(getPosition(), ((ListNode)yyVals[-1+yyTop])));
    return yyVal;
}
public Object case360_line1355(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
}
public Object case344_line1301(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-5+yyTop])), ((ListNode)yyVals[-5+yyTop]), null, ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case12_line338(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new VAliasNode(getPosition(((Token)yyVals[-2+yyTop])), (String) ((Token)yyVals[-1+yyTop]).getValue(), "$" + ((BackRefNode)yyVals[0+yyTop]).getType());
    return yyVal;
}
public Object case98_line665(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_colon3(getPosition(((Token)yyVals[-1+yyTop])), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case225_line901(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newOrNode(getPosition(((Token)yyVals[-1+yyTop])), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case274_line1071(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_yield(getPosition(((Token)yyVals[-3+yyTop])), ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case231_line921(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case32_line431(Object yyVal, Object[] yyVals, int yyTop) {
                    ((MultipleAsgn19Node)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                    yyVal = ((MultipleAsgn19Node)yyVals[-2+yyTop]);
    return yyVal;
}
public Object case448_line1712(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new Token("nil", Tokens.kNIL, ((Token)yyVals[0+yyTop]).getPosition());
    return yyVal;
}
public Object case11_line335(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new VAliasNode(getPosition(((Token)yyVals[-2+yyTop])), (String) ((Token)yyVals[-1+yyTop]).getValue(), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case26_line412(Object yyVal, Object[] yyVals, int yyTop) {
  /* FIXME: arg_concat logic missing for opt_call_args*/
                    yyVal = support.new_opElementAsgnNode(getPosition(((Node)yyVals[-5+yyTop])), ((Node)yyVals[-5+yyTop]), (String) ((Token)yyVals[-1+yyTop]).getValue(), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case540_line2010(Object yyVal, Object[] yyVals, int yyTop) {
                  yyVal = null;
    return yyVal;
}
public Object case388_line1466(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newWhenNode(getPosition(((Token)yyVals[-4+yyTop])), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case530_line1988(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Token)yyVals[0+yyTop]);
    return yyVal;
}
public Object case464_line1768(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ArgsNode)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case393_line1489(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(((Node)yyVals[0+yyTop]).getPosition(), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case386_line1458(Object yyVal, Object[] yyVals, int yyTop) {
                    support.pushBlockScope();
    return yyVal;
}
public Object case332_line1263(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((ListNode)yyVals[-2+yyTop])), ((ListNode)yyVals[-2+yyTop]), new StarNode(getPosition()), null);
    return yyVal;
}
public Object case293_line1129(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newCaseNode(getPosition(((Token)yyVals[-3+yyTop])), null, ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case271_line1056(Object yyVal, Object[] yyVals, int yyTop) {
                    ISourcePosition position = getPosition(((Token)yyVals[-2+yyTop]));
                    if (((Node)yyVals[-1+yyTop]) == null) {
                        yyVal = new ZArrayNode(position); /* zero length array */
                    } else {
                        yyVal = ((Node)yyVals[-1+yyTop]);
                        ((ISourcePositionHolder)yyVal).setPosition(position);
                    }
    return yyVal;
}
public Object case418_line1611(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ArrayNode(getPosition());
    return yyVal;
}
public Object case430_line1656(Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = new InstVarNode(((Token)yyVals[0+yyTop]).getPosition(), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case365_line1373(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new LambdaNode(getPosition(((ArgsNode)yyVals[-1+yyTop])), ((ArgsNode)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), support.getCurrentScope());
                    support.popCurrentScope();
                    lexer.setLeftParenBegin(((Integer)yyVals[-2+yyTop]));
    return yyVal;
}
public Object case356_line1341(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(), null, null, null, null, null);
    return yyVal;
}
public Object case355_line1338(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(), null, null, null, null, null);
    return yyVal;
}
public Object case243_line954(Object yyVal, Object[] yyVals, int yyTop) {
    return yyVal;
}
public Object case185_line731(Object yyVal, Object[] yyVals, int yyTop) {
                    ISourcePosition position = getPosition(((Token)yyVals[-1+yyTop]));
                    Node body = ((Node)yyVals[0+yyTop]) == null ? NilImplicitNode.NIL : ((Node)yyVals[0+yyTop]);
                    yyVal = support.node_assign(((Node)yyVals[-4+yyTop]), new RescueNode(position, ((Node)yyVals[-2+yyTop]), new RescueBodyNode(position, null, body, null), null));
    return yyVal;
}
public Object case289_line1118(Object yyVal, Object[] yyVals, int yyTop) {
                  lexer.getConditionState().begin();
    return yyVal;
}
public Object case503_line1931(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((BlockArgNode)yyVals[0+yyTop]);
    return yyVal;
}
public Object case469_line1784(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-3+yyTop])), ((ListNode)yyVals[-3+yyTop]), null, ((RestArgNode)yyVals[-1+yyTop]), null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case459_line1749(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
}
public Object case425_line1635(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = lexer.getStrTerm();
                    lexer.setStrTerm(null);
                    lexer.setState(LexState.EXPR_BEG);
    return yyVal;
}
public Object case405_line1531(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case331_line1260(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((ListNode)yyVals[-5+yyTop])), ((ListNode)yyVals[-5+yyTop]), support.assignable(((Token)yyVals[-2+yyTop]), null), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case273_line1068(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ReturnNode(((Token)yyVals[0+yyTop]).getPosition(), NilImplicitNode.NIL);
    return yyVal;
}
public Object case266_line1037(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setState(LexState.EXPR_ENDARG); 
    return yyVal;
}
public Object case105_line681(Object yyVal, Object[] yyVals, int yyTop) {
                   lexer.setState(LexState.EXPR_END);
                   yyVal = ((Token)yyVals[0+yyTop]);
    return yyVal;
}
public Object case92_line635(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case420_line1618(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new StrNode(((Token)yyVals[0+yyTop]).getPosition(), ByteList.create(""));
    return yyVal;
}
public Object case342_line1294(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-3+yyTop])), ((ListNode)yyVals[-3+yyTop]), null, ((RestArgNode)yyVals[-1+yyTop]), null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case313_line1213(Object yyVal, Object[] yyVals, int yyTop) {
                    support.checkExpression(((Node)yyVals[0+yyTop]));
                    yyVal = ((Node)yyVals[0+yyTop]);
                    if (yyVal == null) yyVal = NilImplicitNode.NIL;
    return yyVal;
}
public Object case213_line859(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "<", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case272_line1065(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new HashNode(getPosition(((Token)yyVals[-2+yyTop])), ((ListNode)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case250_line976(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newSplatNode(getPosition(((Token)yyVals[-1+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case78_line578(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(((Node)yyVals[0+yyTop]).getPosition(), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case85_line600(Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        yyerror("dynamic constant assignment");
                    }

                    ISourcePosition position = getPosition(((Node)yyVals[-2+yyTop]));

                    yyVal = new ConstDeclNode(position, null, support.new_colon2(position, ((Node)yyVals[-2+yyTop]), (String) ((Token)yyVals[0+yyTop]).getValue()), NilImplicitNode.NIL);
    return yyVal;
}
public Object case63_line528(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((Token)yyVals[-2+yyTop])), support.newArrayNode(getPosition(((Token)yyVals[-2+yyTop])), ((Node)yyVals[-1+yyTop])), null, null);
    return yyVal;
}
public Object case433_line1665(Object yyVal, Object[] yyVals, int yyTop) {
                     lexer.setState(LexState.EXPR_END);
                     yyVal = ((Token)yyVals[0+yyTop]);
                     ((ISourcePositionHolder)yyVal).setPosition(getPosition(((Token)yyVals[-1+yyTop])));
    return yyVal;
}
public Object case71_line554(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((Token)yyVals[-3+yyTop])), null, ((Node)yyVals[-2+yyTop]), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case212_line856(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ">=", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case322_line1232(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case51_line487(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IterNode(getPosition(((Token)yyVals[-4+yyTop])), ((ArgsNode)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), support.getCurrentScope());
                    support.popCurrentScope();
    return yyVal;
}
public Object case90_line629(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case110_line702(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new UndefNode(getPosition(((Token)yyVals[0+yyTop])), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case512_line1968(Object yyVal, Object[] yyVals, int yyTop) {
                    ISourcePosition position;
                    if (((Node)yyVals[-2+yyTop]) == null && ((Node)yyVals[0+yyTop]) == null) {
                        position = getPosition(((Token)yyVals[-1+yyTop]));
                    } else {
                        position = getPosition(((Node)yyVals[-2+yyTop]));
                    }

                    yyVal = support.newArrayNode(position, ((Node)yyVals[-2+yyTop])).add(((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case507_line1946(Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-1+yyTop]) == null) {
                        yyerror("can't define single method for ().");
                    } else if (((Node)yyVals[-1+yyTop]) instanceof ILiteralNode) {
                        yyerror("can't define single method for literals.");
                    }
                    support.checkExpression(((Node)yyVals[-1+yyTop]));
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case416_line1603(Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = new ZArrayNode(getPosition(((Token)yyVals[-2+yyTop])));
    return yyVal;
}
public Object case410_line1584(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ZArrayNode(getPosition(((Token)yyVals[-2+yyTop])));
    return yyVal;
}
public Object case401_line1509(Object yyVal, Object[] yyVals, int yyTop) {
                    /* FIXME: We may be intern'ing more than once.*/
                    yyVal = new SymbolNode(((Token)yyVals[0+yyTop]).getPosition(), ((String) ((Token)yyVals[0+yyTop]).getValue()).intern());
    return yyVal;
}
public Object case276_line1077(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new YieldNode(((Token)yyVals[0+yyTop]).getPosition(), null, false);
    return yyVal;
}
public Object case326_line1243(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case277_line1080(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new DefinedNode(getPosition(((Token)yyVals[-4+yyTop])), ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case227_line908(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IfNode(getPosition(((Node)yyVals[-5+yyTop])), support.getConditionNode(((Node)yyVals[-5+yyTop])), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case226_line904(Object yyVal, Object[] yyVals, int yyTop) {
                    /* ENEBO: arg surrounded by in_defined set/unset*/
                    yyVal = new DefinedNode(getPosition(((Token)yyVals[-2+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case58_line511(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_super(((Node)yyVals[0+yyTop]), ((Token)yyVals[-1+yyTop])); /* .setPosFrom($2);*/
    return yyVal;
}
public Object case482_line1824(Object yyVal, Object[] yyVals, int yyTop) {
                    yyerror("formal argument cannot be a global variable");
    return yyVal;
}
public Object case478_line1811(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((BlockArgNode)yyVals[0+yyTop]).getPosition(), null, null, null, null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case404_line1528(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new StrNode(((Token)yyVals[-1+yyTop]).getPosition(), ByteList.create((String) ((Token)yyVals[0+yyTop]).getValue()));
    return yyVal;
}
public Object case391_line1472(Object yyVal, Object[] yyVals, int yyTop) {
                    Node node;
                    if (((Node)yyVals[-3+yyTop]) != null) {
                        node = support.appendToBlock(support.node_assign(((Node)yyVals[-3+yyTop]), new GlobalVarNode(getPosition(((Token)yyVals[-5+yyTop])), "$!")), ((Node)yyVals[-1+yyTop]));
                        if (((Node)yyVals[-1+yyTop]) != null) {
                            node.setPosition(support.unwrapNewlineNode(((Node)yyVals[-1+yyTop])).getPosition());
                        }
                    } else {
                        node = ((Node)yyVals[-1+yyTop]);
                    }
                    Node body = node == null ? NilImplicitNode.NIL : node;
                    yyVal = new RescueBodyNode(getPosition(((Token)yyVals[-5+yyTop])), ((Node)yyVals[-4+yyTop]), body, ((RescueBodyNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case333_line1266(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((ListNode)yyVals[-4+yyTop])), ((ListNode)yyVals[-4+yyTop]), new StarNode(getPosition()), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case44_line465(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ReturnNode(getPosition(((Token)yyVals[-1+yyTop])), support.ret_args(((Node)yyVals[0+yyTop]), getPosition(((Token)yyVals[-1+yyTop]))));
    return yyVal;
}
public Object case217_line871(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "!=", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case81_line588(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.aryset(((Node)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case50_line485(Object yyVal, Object[] yyVals, int yyTop) {
                    support.pushBlockScope();
    return yyVal;
}
public Object case65_line536(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((Node)yyVals[-1+yyTop])), ((ListNode)yyVals[-1+yyTop]).add(((Node)yyVals[0+yyTop])), null, null);
    return yyVal;
}
public Object case206_line838(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[0+yyTop]), "-@");
    return yyVal;
}
public Object case37_line447(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newOrNode(getPosition(((Token)yyVals[-1+yyTop])), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case460_line1752(Object yyVal, Object[] yyVals, int yyTop) {
                   lexer.setState(LexState.EXPR_BEG);
    return yyVal;
}
public Object case312_line1209(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new RetryNode(((Token)yyVals[0+yyTop]).getPosition());
    return yyVal;
}
public Object case192_line784(Object yyVal, Object[] yyVals, int yyTop) {
                    yyerror("constant re-assignment");
    return yyVal;
}
public Object case80_line585(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.assignable(((Token)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
}
public Object case64_line533(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((ListNode)yyVals[0+yyTop])), ((ListNode)yyVals[0+yyTop]), null, null);
    return yyVal;
}
public Object case428_line1647(Object yyVal, Object[] yyVals, int yyTop) {
                   lexer.setStrTerm(((StrTerm)yyVals[-2+yyTop]));

                   yyVal = support.newEvStrNode(getPosition(((Token)yyVals[-3+yyTop])), ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case384_line1452(Object yyVal, Object[] yyVals, int yyTop) {
                    support.pushBlockScope();
    return yyVal;
}
public Object case375_line1420(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_fcall(((Token)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case372_line1402(Object yyVal, Object[] yyVals, int yyTop) {
                    /* Workaround for JRUBY-2326 (MRI does not enter this production for some reason)*/
                    if (((Node)yyVals[-1+yyTop]) instanceof YieldNode) {
                        throw new SyntaxException(PID.BLOCK_GIVEN_TO_YIELD, getPosition(((Node)yyVals[-1+yyTop])), lexer.getCurrentLine(), "block given to yield");
                    }
                    if (((BlockAcceptingNode)yyVals[-1+yyTop]).getIterNode() instanceof BlockPassNode) {
                        throw new SyntaxException(PID.BLOCK_ARG_AND_BLOCK_GIVEN, getPosition(((Node)yyVals[-1+yyTop])), lexer.getCurrentLine(), "Both block arg and actual block given.");
                    }
                    yyVal = ((BlockAcceptingNode)yyVals[-1+yyTop]).setIterNode(((IterNode)yyVals[0+yyTop]));
                    ((Node)yyVal).setPosition(getPosition(((Node)yyVals[-1+yyTop])));
    return yyVal;
}
public Object case337_line1278(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((Token)yyVals[-2+yyTop])), null, null, ((ListNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case4_line312(Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-1+yyTop]) instanceof BlockNode) {
                        support.checkUselessStatements(((BlockNode)yyVals[-1+yyTop]));
                    }
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case294_line1132(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getConditionState().begin();
    return yyVal;
}
public Object case17_line353(Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-2+yyTop]) != null && ((Node)yyVals[-2+yyTop]) instanceof BeginNode) {
                        yyVal = new WhileNode(getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), ((BeginNode)yyVals[-2+yyTop]).getBodyNode(), false);
                    } else {
                        yyVal = new WhileNode(getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), ((Node)yyVals[-2+yyTop]), true);
                    }
    return yyVal;
}
public Object case82_line591(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case309_line1200(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BreakNode(((Token)yyVals[0+yyTop]).getPosition(), NilImplicitNode.NIL);
    return yyVal;
}
public Object case199_line813(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "*", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case38_line450(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new NotNode(getPosition(((Token)yyVals[-1+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])));
    return yyVal;
}
public Object case398_line1503(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case338_line1282(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-5+yyTop])), ((ListNode)yyVals[-5+yyTop]), ((ListNode)yyVals[-3+yyTop]), ((RestArgNode)yyVals[-1+yyTop]), null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case224_line898(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newAndNode(getPosition(((Token)yyVals[-1+yyTop])), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case283_line1101(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((LambdaNode)yyVals[0+yyTop]);
    return yyVal;
}
public Object case379_line1432(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-2+yyTop]), new Token("call", ((Node)yyVals[-2+yyTop]).getPosition()), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case100_line671(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_colon2(getPosition(((Node)yyVals[-2+yyTop])), ((Node)yyVals[-2+yyTop]), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case223_line895(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ">>", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case210_line850(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "<=>", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case311_line1206(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new RedoNode(((Token)yyVals[0+yyTop]).getPosition());
    return yyVal;
}
public Object case287_line1112(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getConditionState().end();
    return yyVal;
}
public Object case488_line1865(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ArrayNode(getPosition(), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case455_line1734(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.gettable(((Token)yyVals[0+yyTop]));
    return yyVal;
}
public Object case440_line1701(Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((FloatNode)yyVals[0+yyTop]);
    return yyVal;
}
public Object case429_line1653(Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = new GlobalVarNode(((Token)yyVals[0+yyTop]).getPosition(), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case340_line1288(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-3+yyTop])), ((ListNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), null, null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case336_line1275(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((Token)yyVals[0+yyTop])), null, new StarNode(getPosition()), null);
    return yyVal;
}
public Object case508_line1956(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ArrayNode(getPosition());
    return yyVal;
}
public Object case477_line1808(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((RestArgNode)yyVals[-3+yyTop])), null, null, ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case421_line1621(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.literal_concat(getPosition(((Node)yyVals[-1+yyTop])), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case407_line1538(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);

                    ((ISourcePositionHolder)yyVal).setPosition(getPosition(((Token)yyVals[-2+yyTop])));
                    int extraLength = ((String) ((Token)yyVals[-2+yyTop]).getValue()).length() - 1;

                    /* We may need to subtract addition offset off of first */
                    /* string fragment (we optimistically take one off in*/
                    /* ParserSupport.literal_concat).  Check token length*/
                    /* and subtract as neeeded.*/
                    if ((((Node)yyVals[-1+yyTop]) instanceof DStrNode) && extraLength > 0) {
                      Node strNode = ((DStrNode)((Node)yyVals[-1+yyTop])).get(0);
                    }
    return yyVal;
}
public Object case381_line1438(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_super(((Node)yyVals[0+yyTop]), ((Token)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case253_line1000(Object yyVal, Object[] yyVals, int yyTop) {
                    Node node = support.splat_array(((Node)yyVals[-2+yyTop]));

                    if (node != null) {
                        yyVal = support.list_append(node, ((Node)yyVals[0+yyTop]));
                    } else {
                        yyVal = support.arg_append(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
                    }
    return yyVal;
}
public Object case197_line807(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "+", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case25_line396(Object yyVal, Object[] yyVals, int yyTop) {
                    support.checkExpression(((Node)yyVals[0+yyTop]));

                    String asgnOp = (String) ((Token)yyVals[-1+yyTop]).getValue();
                    if (asgnOp.equals("||")) {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                        yyVal = new OpAsgnOrNode(getPosition(((AssignableNode)yyVals[-2+yyTop])), support.gettable2(((AssignableNode)yyVals[-2+yyTop])), ((AssignableNode)yyVals[-2+yyTop]));
                    } else if (asgnOp.equals("&&")) {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                        yyVal = new OpAsgnAndNode(getPosition(((AssignableNode)yyVals[-2+yyTop])), support.gettable2(((AssignableNode)yyVals[-2+yyTop])), ((AssignableNode)yyVals[-2+yyTop]));
                    } else {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(support.getOperatorCallNode(support.gettable2(((AssignableNode)yyVals[-2+yyTop])), asgnOp, ((Node)yyVals[0+yyTop])));
                        ((AssignableNode)yyVals[-2+yyTop]).setPosition(getPosition(((AssignableNode)yyVals[-2+yyTop])));
                        yyVal = ((AssignableNode)yyVals[-2+yyTop]);
                    }
    return yyVal;
}
public Object case28_line419(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new OpAsgnNode(getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((Node)yyVals[0+yyTop]), (String) ((Token)yyVals[-2+yyTop]).getValue(), (String) ((Token)yyVals[-1+yyTop]).getValue());
    return yyVal;
}
public Object case10_line332(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new AliasNode(getPosition(((Token)yyVals[-3+yyTop])), (String) ((Token)yyVals[-2+yyTop]).getValue(), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case297_line1140(Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        yyerror("class definition in method body");
                    }
                    support.pushLocalScope();
    return yyVal;
}
public Object case531_line1991(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Token)yyVals[0+yyTop]);
    return yyVal;
}
public Object case494_line1898(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BlockNode(getPosition(((Node)yyVals[0+yyTop]))).add(((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case472_line1793(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-3+yyTop])), null, ((ListNode)yyVals[-3+yyTop]), ((RestArgNode)yyVals[-1+yyTop]), null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case249_line973(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(getPosition2(((Node)yyVals[0+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case303_line1169(Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = ((Node)yyVals[-1+yyTop]) == null ? NilImplicitNode.NIL : ((Node)yyVals[-1+yyTop]);

                    yyVal = new ModuleNode(getPosition(((Token)yyVals[-4+yyTop])), ((Colon3Node)yyVals[-3+yyTop]), support.getCurrentScope(), body);
                    support.popCurrentScope();
    return yyVal;
}
public Object case13_line341(Object yyVal, Object[] yyVals, int yyTop) {
                    yyerror("can't make alias for the number variables");
    return yyVal;
}
public Object case465_line1772(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-5+yyTop])), ((ListNode)yyVals[-5+yyTop]), ((ListNode)yyVals[-3+yyTop]), ((RestArgNode)yyVals[-1+yyTop]), null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case302_line1164(Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) { 
                        yyerror("module definition in method body");
                    }
                    support.pushLocalScope();
    return yyVal;
}
public Object case299_line1151(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new Boolean(support.isInDef());
                    support.setInDef(false);
    return yyVal;
}
public Object case245_line959(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getCmdArgumentState().reset(((Long)yyVals[-1+yyTop]).longValue());
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case351_line1322(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((RestArgNode)yyVals[-3+yyTop])), null, null, ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case350_line1319(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((RestArgNode)yyVals[-1+yyTop])), null, null, ((RestArgNode)yyVals[-1+yyTop]), null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case30_line425(Object yyVal, Object[] yyVals, int yyTop) {
                    support.backrefAssignError(((Node)yyVals[-2+yyTop]));
    return yyVal;
}
public Object case370_line1395(Object yyVal, Object[] yyVals, int yyTop) {
                    support.pushBlockScope();
    return yyVal;
}
public Object case347_line1310(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-5+yyTop])), null, ((ListNode)yyVals[-5+yyTop]), ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case304_line1175(Object yyVal, Object[] yyVals, int yyTop) {
                    support.setInDef(true);
                    support.pushLocalScope();
    return yyVal;
}
public Object case462_line1757(Object yyVal, Object[] yyVals, int yyTop) {
                   yyerrok();
                   yyVal = null;
    return yyVal;
}
public Object case409_line1569(Object yyVal, Object[] yyVals, int yyTop) {
                    int options = ((RegexpNode)yyVals[0+yyTop]).getOptions();
                    Node node = ((Node)yyVals[-1+yyTop]);

                    if (node == null) {
                        yyVal = new RegexpNode(getPosition(((Token)yyVals[-2+yyTop])), ByteList.create(""), options & ~ReOptions.RE_OPTION_ONCE);
                    } else if (node instanceof StrNode) {
                        yyVal = new RegexpNode(((Node)yyVals[-1+yyTop]).getPosition(), (ByteList) ((StrNode) node).getValue().clone(), options & ~ReOptions.RE_OPTION_ONCE);
                    } else if (node instanceof DStrNode) {
                        yyVal = new DRegexpNode(getPosition(((Token)yyVals[-2+yyTop])), (DStrNode) node, options, (options & ReOptions.RE_OPTION_ONCE) != 0);
                    } else {
                        yyVal = new DRegexpNode(getPosition(((Token)yyVals[-2+yyTop])), options, (options & ReOptions.RE_OPTION_ONCE) != 0).add(node);
                    }
    return yyVal;
}
public Object case363_line1365(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
}
public Object case204_line828(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(support.getOperatorCallNode(((FloatNode)yyVals[-2+yyTop]), "**", ((Node)yyVals[0+yyTop]), getPosition()), "-@");
    return yyVal;
}
public Object case296_line1136(Object yyVal, Object[] yyVals, int yyTop) {
                      /* ENEBO: Lots of optz in 1.9 parser here*/
                    yyVal = new ForNode(getPosition(((Token)yyVals[-8+yyTop])), ((Node)yyVals[-7+yyTop]), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[-4+yyTop]));
    return yyVal;
}
public Object case269_line1050(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_colon2(getPosition(((Node)yyVals[-2+yyTop])), ((Node)yyVals[-2+yyTop]), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case109_line698(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case301_line1158(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new SClassNode(getPosition(((Token)yyVals[-7+yyTop])), ((Node)yyVals[-5+yyTop]), support.getCurrentScope(), ((Node)yyVals[-1+yyTop]));
                    support.popCurrentScope();
                    support.setInDef(((Boolean)yyVals[-4+yyTop]).booleanValue());
                    support.setInSingle(((Integer)yyVals[-2+yyTop]).intValue());
    return yyVal;
}
public Object case241_line946(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(getPosition(((ListNode)yyVals[-1+yyTop])), new HashNode(getPosition(), ((ListNode)yyVals[-1+yyTop])));
                    yyVal = support.arg_blk_pass((Node)yyVal, ((BlockPassNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case69_line548(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((ListNode)yyVals[-3+yyTop])), ((ListNode)yyVals[-3+yyTop]), new StarNode(getPosition()), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case453_line1727(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new Token("__LINE__", Tokens.k__LINE__, ((Token)yyVals[0+yyTop]).getPosition());
    return yyVal;
}
public Object case396_line1498(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case377_line1426(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((Token)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case205_line831(Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isLiteral(((Node)yyVals[0+yyTop]))) {
                        yyVal = ((Node)yyVals[0+yyTop]);
                    } else {
                        yyVal = support.getOperatorCallNode(((Node)yyVals[0+yyTop]), "+@");
                    }
    return yyVal;
}
public Object case112_line707(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.appendToBlock(((Node)yyVals[-3+yyTop]), new UndefNode(getPosition(((Node)yyVals[-3+yyTop])), (String) ((Token)yyVals[0+yyTop]).getValue()));
    return yyVal;
}
public Object case467_line1778(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-3+yyTop])), ((ListNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), null, null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case56_line505(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((Token)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case285_line1107(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IfNode(getPosition(((Token)yyVals[-5+yyTop])), support.getConditionNode(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[-2+yyTop]));
    return yyVal;
}
public Object case62_line525(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((MultipleAsgn19Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case89_line626(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.aryset(((Node)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case359_line1350(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
}
public Object case55_line502(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-4+yyTop]), ((Token)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), ((IterNode)yyVals[0+yyTop])); 
    return yyVal;
}
public Object case242_line950(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.arg_append(((Node)yyVals[-3+yyTop]), new HashNode(getPosition(), ((ListNode)yyVals[-1+yyTop])));
                    yyVal = support.arg_blk_pass((Node)yyVal, ((BlockPassNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case215_line865(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "==", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case346_line1307(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-3+yyTop])), null, ((ListNode)yyVals[-3+yyTop]), ((RestArgNode)yyVals[-1+yyTop]), null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case341_line1291(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-5+yyTop])), ((ListNode)yyVals[-5+yyTop]), ((ListNode)yyVals[-3+yyTop]), null, ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case290_line1120(Object yyVal, Object[] yyVals, int yyTop) {
                  lexer.getConditionState().end();
    return yyVal;
}
public Object case73_line560(Object yyVal, Object[] yyVals, int yyTop) {
                      yyVal = new MultipleAsgn19Node(getPosition(((Token)yyVals[-2+yyTop])), null, new StarNode(getPosition()), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case214_line862(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "<=", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case190_line778(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new OpAsgnNode(getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((Node)yyVals[0+yyTop]), (String) ((Token)yyVals[-2+yyTop]).getValue(), (String) ((Token)yyVals[-1+yyTop]).getValue());
    return yyVal;
}
public Object case538_line2002(Object yyVal, Object[] yyVals, int yyTop) {
                      yyerrok();
    return yyVal;
}
public Object case535_line1996(Object yyVal, Object[] yyVals, int yyTop) {
                      yyerrok();
    return yyVal;
}
public Object case492_line1891(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BlockNode(getPosition(((Node)yyVals[0+yyTop]))).add(((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case91_line632(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case15_line347(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IfNode(getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), ((Node)yyVals[-2+yyTop]), null);
    return yyVal;
}
public Object case21_line376(Object yyVal, Object[] yyVals, int yyTop) {
                    support.getResult().addBeginNode(new PreExeNode(getPosition(((Node)yyVals[-1+yyTop])), support.getCurrentScope(), ((Node)yyVals[-1+yyTop])));
                    support.popCurrentScope();
                    yyVal = null;
    return yyVal;
}
public Object case511_line1964(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).addAll(((ListNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case474_line1799(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-1+yyTop])), null, ((ListNode)yyVals[-1+yyTop]), null, null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case458_line1745(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case423_line1628(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.literal_concat(getPosition(((Node)yyVals[-1+yyTop])), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case228_line911(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case67_line542(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((ListNode)yyVals[-4+yyTop])), ((ListNode)yyVals[-4+yyTop]), ((Node)yyVals[-2+yyTop]), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case208_line844(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "^", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case41_line458(Object yyVal, Object[] yyVals, int yyTop) {
                    support.checkExpression(((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case191_line781(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new OpAsgnNode(getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((Node)yyVals[0+yyTop]), (String) ((Token)yyVals[-2+yyTop]).getValue(), (String) ((Token)yyVals[-1+yyTop]).getValue());
    return yyVal;
}
public Object case23_line387(Object yyVal, Object[] yyVals, int yyTop) {
                    support.checkExpression(((Node)yyVals[0+yyTop]));
                    yyVal = support.node_assign(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case229_line915(Object yyVal, Object[] yyVals, int yyTop) {
                    support.checkExpression(((Node)yyVals[0+yyTop]));
                    yyVal = ((Node)yyVals[0+yyTop]) != null ? ((Node)yyVals[0+yyTop]) : NilImplicitNode.NIL;
    return yyVal;
}
public Object case424_line1632(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case361_line1358(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
}
public Object case29_line422(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new OpAsgnNode(getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((Node)yyVals[0+yyTop]), (String) ((Token)yyVals[-2+yyTop]).getValue(), (String) ((Token)yyVals[-1+yyTop]).getValue());
    return yyVal;
}
public Object case307_line1188(Object yyVal, Object[] yyVals, int yyTop) {
                    support.setInSingle(support.getInSingle() + 1);
                    support.pushLocalScope();
                    lexer.setState(LexState.EXPR_END); /* force for args */
    return yyVal;
}
public Object case275_line1074(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new YieldNode(getPosition(((Token)yyVals[-2+yyTop])), null, false);
    return yyVal;
}
public Object case325_line1240(Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = support.assignable(((Token)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
}
public Object case221_line889(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[0+yyTop]), "~");
    return yyVal;
}
public Object case506_line1944(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setState(LexState.EXPR_BEG);
    return yyVal;
}
public Object case408_line1553(Object yyVal, Object[] yyVals, int yyTop) {
                    ISourcePosition position = getPosition(((Token)yyVals[-2+yyTop]));

                    if (((Node)yyVals[-1+yyTop]) == null) {
                        yyVal = new XStrNode(position, null);
                    } else if (((Node)yyVals[-1+yyTop]) instanceof StrNode) {
                        yyVal = new XStrNode(position, (ByteList) ((StrNode)yyVals[-1+yyTop]).getValue().clone());
                    } else if (((Node)yyVals[-1+yyTop]) instanceof DStrNode) {
                        yyVal = new DXStrNode(position, ((DStrNode)yyVals[-1+yyTop]));

                        ((Node)yyVal).setPosition(position);
                    } else {
                        yyVal = new DXStrNode(position).add(((Node)yyVals[-1+yyTop]));
                    }
    return yyVal;
}
public Object case52_line493(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_fcall(((Token)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case480_line1818(Object yyVal, Object[] yyVals, int yyTop) {
                    yyerror("formal argument cannot be a constant");
    return yyVal;
}
public Object case369_line1391(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case368_line1388(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case2_line282(Object yyVal, Object[] yyVals, int yyTop) {
  /* ENEBO: Removed !compile_for_eval which probably is to reduce warnings*/
                  if (((Node)yyVals[0+yyTop]) != null) {
                      /* last expression should not be void */
                      if (((Node)yyVals[0+yyTop]) instanceof BlockNode) {
                          support.checkUselessStatement(((BlockNode)yyVals[0+yyTop]).getLast());
                      } else {
                          support.checkUselessStatement(((Node)yyVals[0+yyTop]));
                      }
                  }
                  support.getResult().setAST(support.addRootNode(((Node)yyVals[0+yyTop]), getPosition(((Node)yyVals[0+yyTop]))));
    return yyVal;
}
public Object case279_line1086(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(NilImplicitNode.NIL, "!");
    return yyVal;
}
public Object case24_line391(Object yyVal, Object[] yyVals, int yyTop) {
                    support.checkExpression(((Node)yyVals[0+yyTop]));
                    ((MultipleAsgn19Node)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                    yyVal = ((MultipleAsgn19Node)yyVals[-2+yyTop]);
    return yyVal;
}
public Object case485_line1833(Object yyVal, Object[] yyVals, int yyTop) {
    /* FIXME: Resolve what the hell is going on*/
    /*                    if (support.is_local_id($1)) {
                        yyerror("formal argument must be local variable");
                        }*/
                     
                    support.shadowing_lvar(((Token)yyVals[0+yyTop]));
                    yyVal = ((Token)yyVals[0+yyTop]);
    return yyVal;
}
public Object case413_line1594(Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[-1+yyTop]) instanceof EvStrNode ? new DStrNode(getPosition(((ListNode)yyVals[-2+yyTop]))).add(((Node)yyVals[-1+yyTop])) : ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case498_line1907(Object yyVal, Object[] yyVals, int yyTop) {
                    if (!support.is_local_id(((Token)yyVals[0+yyTop]))) {
                        yyerror("duplicate rest argument name");
                    }
                    support.shadowing_lvar(((Token)yyVals[0+yyTop]));
                    yyVal = new RestArgNode(getPosition(((Token)yyVals[-1+yyTop])), (String) ((Token)yyVals[0+yyTop]).getValue(), support.arg_var(((Token)yyVals[0+yyTop])));
    return yyVal;
}
public Object case486_line1843(Object yyVal, Object[] yyVals, int yyTop) {
                    support.arg_var(((Token)yyVals[0+yyTop]));
                    yyVal = new ArgumentNode(((ISourcePositionHolder)yyVals[0+yyTop]).getPosition(), (String) ((Token)yyVals[0+yyTop]).getValue());
  /*
                    $$ = new ArgAuxiliaryNode($1.getPosition(), (String) $1.getValue(), 1);
  */
    return yyVal;
}
public Object case419_line1614(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case387_line1460(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IterNode(getPosition(((Token)yyVals[-4+yyTop])), ((ArgsNode)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), support.getCurrentScope());
                    ((ISourcePositionHolder)yyVals[-5+yyTop]).setPosition(getPosition(((ISourcePositionHolder)yyVals[-5+yyTop])));
                    support.popCurrentScope();
    return yyVal;
}
public Object case36_line444(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newAndNode(getPosition(((Token)yyVals[-1+yyTop])), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case329_line1254(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((ListNode)yyVals[0+yyTop])), ((ListNode)yyVals[0+yyTop]), null, null);
    return yyVal;
}
public Object case451_line1721(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new Token("false", Tokens.kFALSE, ((Token)yyVals[0+yyTop]).getPosition());
    return yyVal;
}
public Object case403_line1515(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]) instanceof EvStrNode ? new DStrNode(getPosition(((Node)yyVals[0+yyTop]))).add(((Node)yyVals[0+yyTop])) : ((Node)yyVals[0+yyTop]);
                    /*
                    NODE *node = $1;
                    if (!node) {
                        node = NEW_STR(STR_NEW0());
                    } else {
                        node = evstr2dstr(node);
                    }
                    $$ = node;
                    */
    return yyVal;
}
public Object case267_line1039(Object yyVal, Object[] yyVals, int yyTop) {
                    if (warnings.isVerbose()) warnings.warning(ID.GROUPED_EXPRESSION, getPosition(((Token)yyVals[-3+yyTop])), "(...) interpreted as grouped expression");
                    yyVal = ((Node)yyVals[-2+yyTop]);
    return yyVal;
}
public Object case463_line1763(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ArgsNode)yyVals[-1+yyTop]);
                    ((ISourcePositionHolder)yyVal).setPosition(getPosition(((Token)yyVals[-2+yyTop])));
                    lexer.setState(LexState.EXPR_BEG);
    return yyVal;
}
public Object case450_line1718(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new Token("true", Tokens.kTRUE, ((Token)yyVals[0+yyTop]).getPosition());
    return yyVal;
}
public Object case406_line1534(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.literal_concat(getPosition(((Node)yyVals[-1+yyTop])), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case328_line1250(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case286_line1110(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getConditionState().begin();
    return yyVal;
}
public Object case198_line810(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "-", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
public Object case104_line677(Object yyVal, Object[] yyVals, int yyTop) {
                   lexer.setState(LexState.EXPR_END);
                   yyVal = ((Token)yyVals[0+yyTop]);
    return yyVal;
}
public Object case483_line1827(Object yyVal, Object[] yyVals, int yyTop) {
                    yyerror("formal argument cannot be a class variable");
    return yyVal;
}
public Object case439_line1698(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case392_line1485(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null; 
    return yyVal;
}
public Object case380_line1435(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-2+yyTop]), new Token("call", ((Node)yyVals[-2+yyTop]).getPosition()), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case373_line1413(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((Token)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case95_line656(Object yyVal, Object[] yyVals, int yyTop) {
                    support.backrefAssignError(((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case487_line1850(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
                    /*		    {
			ID tid = internal_id();
			arg_var(tid);
			if (dyna_in_block()) {
			    $2->nd_value = NEW_DVAR(tid);
			}
			else {
			    $2->nd_value = NEW_LVAR(tid);
			}
			$$ = NEW_ARGS_AUX(tid, 1);
			$$->nd_next = $2;*/
    return yyVal;
}
public Object case442_line1707(Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = support.negateFloat(((FloatNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case343_line1297(Object yyVal, Object[] yyVals, int yyTop) {
    /* FIXME, weird unnamed rest*/
                    yyVal = support.new_args(((ListNode)yyVals[-1+yyTop]).getPosition(), ((ListNode)yyVals[-1+yyTop]), null, null, null, null);
    return yyVal;
}
public Object case93_line638(Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        yyerror("dynamic constant assignment");
                    }

                    ISourcePosition position = getPosition(((Node)yyVals[-2+yyTop]));

                    yyVal = new ConstDeclNode(position, null, support.new_colon2(position, ((Node)yyVals[-2+yyTop]), (String) ((Token)yyVals[0+yyTop]).getValue()), NilImplicitNode.NIL);
    return yyVal;
}
public Object case292_line1126(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newCaseNode(getPosition(((Token)yyVals[-4+yyTop])), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case87_line618(Object yyVal, Object[] yyVals, int yyTop) {
                    support.backrefAssignError(((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case339_line1285(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-7+yyTop])), ((ListNode)yyVals[-7+yyTop]), ((ListNode)yyVals[-5+yyTop]), ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case108_line695(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Token)yyVals[0+yyTop]);
    return yyVal;
}
public Object case186_line736(Object yyVal, Object[] yyVals, int yyTop) {
                    support.checkExpression(((Node)yyVals[0+yyTop]));
                    String asgnOp = (String) ((Token)yyVals[-1+yyTop]).getValue();

                    if (asgnOp.equals("||")) {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                        yyVal = new OpAsgnOrNode(getPosition(((AssignableNode)yyVals[-2+yyTop])), support.gettable2(((AssignableNode)yyVals[-2+yyTop])), ((AssignableNode)yyVals[-2+yyTop]));
                    } else if (asgnOp.equals("&&")) {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                        yyVal = new OpAsgnAndNode(getPosition(((AssignableNode)yyVals[-2+yyTop])), support.gettable2(((AssignableNode)yyVals[-2+yyTop])), ((AssignableNode)yyVals[-2+yyTop]));
                    } else {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(support.getOperatorCallNode(support.gettable2(((AssignableNode)yyVals[-2+yyTop])), asgnOp, ((Node)yyVals[0+yyTop])));
                        ((AssignableNode)yyVals[-2+yyTop]).setPosition(getPosition(((AssignableNode)yyVals[-2+yyTop])));
                        yyVal = ((AssignableNode)yyVals[-2+yyTop]);
                    }
    return yyVal;
}
public Object case48_line477(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((Token)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case19_line367(Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = ((Node)yyVals[0+yyTop]) == null ? NilImplicitNode.NIL : ((Node)yyVals[0+yyTop]);
                    yyVal = new RescueNode(getPosition(((Node)yyVals[-2+yyTop])), ((Node)yyVals[-2+yyTop]), new RescueBodyNode(getPosition(((Node)yyVals[-2+yyTop])), null, body, null), null);
    return yyVal;
}
public Object case96_line660(Object yyVal, Object[] yyVals, int yyTop) {
                    yyerror("class/module name must be CONSTANT");
    return yyVal;
}
public Object case68_line545(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((ListNode)yyVals[-1+yyTop])), ((ListNode)yyVals[-1+yyTop]), new StarNode(getPosition()), null);
    return yyVal;
}
public Object case247_line968(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((BlockPassNode)yyVals[0+yyTop]);
    return yyVal;
}
public Object case187_line752(Object yyVal, Object[] yyVals, int yyTop) {
                    support.checkExpression(((Node)yyVals[-2+yyTop]));
                    ISourcePosition position = getPosition(((Token)yyVals[-1+yyTop]));
                    Node body = ((Node)yyVals[0+yyTop]) == null ? NilImplicitNode.NIL : ((Node)yyVals[0+yyTop]);
                    Node rescueNode = new RescueNode(position, ((Node)yyVals[-2+yyTop]), new RescueBodyNode(position, null, body, null), null);

                    String asgnOp = (String) ((Token)yyVals[-3+yyTop]).getValue();
                    if (asgnOp.equals("||")) {
                        ((AssignableNode)yyVals[-4+yyTop]).setValueNode(((Node)yyVals[-2+yyTop]));
                        yyVal = new OpAsgnOrNode(getPosition(((AssignableNode)yyVals[-4+yyTop])), support.gettable2(((AssignableNode)yyVals[-4+yyTop])), ((AssignableNode)yyVals[-4+yyTop]));
                    } else if (asgnOp.equals("&&")) {
                        ((AssignableNode)yyVals[-4+yyTop]).setValueNode(((Node)yyVals[-2+yyTop]));
                        yyVal = new OpAsgnAndNode(getPosition(((AssignableNode)yyVals[-4+yyTop])), support.gettable2(((AssignableNode)yyVals[-4+yyTop])), ((AssignableNode)yyVals[-4+yyTop]));
                    } else {
                        ((AssignableNode)yyVals[-4+yyTop]).setValueNode(support.getOperatorCallNode(support.gettable2(((AssignableNode)yyVals[-4+yyTop])), asgnOp, ((Node)yyVals[-2+yyTop])));
                        ((AssignableNode)yyVals[-4+yyTop]).setPosition(getPosition(((AssignableNode)yyVals[-4+yyTop])));
                        yyVal = ((AssignableNode)yyVals[-4+yyTop]);
                    }
    return yyVal;
}
public Object case77_line573(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[-1+yyTop]));
    return yyVal;
}
public Object case84_line597(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case240_line943(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.arg_blk_pass(((Node)yyVals[-1+yyTop]), ((BlockPassNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case489_line1868(Object yyVal, Object[] yyVals, int yyTop) {
                    ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[0+yyTop]));
                    yyVal = ((ListNode)yyVals[-2+yyTop]);
    return yyVal;
}
public Object case468_line1781(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-5+yyTop])), ((ListNode)yyVals[-5+yyTop]), ((ListNode)yyVals[-3+yyTop]), null, ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case441_line1704(Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = support.negateInteger(((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case411_line1587(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case49_line480(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((Token)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case54_line499(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((Token)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case195_line793(Object yyVal, Object[] yyVals, int yyTop) {
                    support.checkExpression(((Node)yyVals[-2+yyTop]));
                    support.checkExpression(((Node)yyVals[0+yyTop]));
    
                    boolean isLiteral = ((Node)yyVals[-2+yyTop]) instanceof FixnumNode && ((Node)yyVals[0+yyTop]) instanceof FixnumNode;
                    yyVal = new DotNode(getPosition(((Node)yyVals[-2+yyTop])), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]), false, isLiteral);
    return yyVal;
}
public Object case509_line1959(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case479_line1814(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(), null, null, null, null, null);
    return yyVal;
}
public Object case461_line1754(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case308_line1192(Object yyVal, Object[] yyVals, int yyTop) {
                    /* TODO: We should use implicit nil for body, but problem (punt til later)*/
                    Node body = ((Node)yyVals[-1+yyTop]); /*$8 == null ? NilImplicitNode.NIL : $8;*/

                    yyVal = new DefsNode(getPosition(((Token)yyVals[-8+yyTop])), ((Node)yyVals[-7+yyTop]), new ArgumentNode(((Token)yyVals[-4+yyTop]).getPosition(), (String) ((Token)yyVals[-4+yyTop]).getValue()), ((ArgsNode)yyVals[-2+yyTop]), support.getCurrentScope(), body);
                    support.popCurrentScope();
                    support.setInSingle(support.getInSingle() - 1);
    return yyVal;
}
public Object case75_line565(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case327_line1247(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(((Node)yyVals[0+yyTop]).getPosition(), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case111_line705(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setState(LexState.EXPR_FNAME);
    return yyVal;
}
public Object case470_line1787(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((ListNode)yyVals[-5+yyTop])), ((ListNode)yyVals[-5+yyTop]), null, ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case415_line1599(Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = support.literal_concat(getPosition(((Node)yyVals[-1+yyTop])), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case385_line1454(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IterNode(getPosition(((Token)yyVals[-4+yyTop])), ((ArgsNode)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), support.getCurrentScope());
                    support.popCurrentScope();
    return yyVal;
}
public Object case353_line1329(Object yyVal, Object[] yyVals, int yyTop) {
    /* was $$ = null;*/
                   yyVal = support.new_args(getPosition(), null, null, null, null, null);
    return yyVal;
}
public Object case8_line326(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
}
public Object case79_line581(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[0+yyTop]));
    return yyVal;
}
public Object case282_line1093(Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-1+yyTop]) != null && 
                          ((BlockAcceptingNode)yyVals[-1+yyTop]).getIterNode() instanceof BlockPassNode) {
                        throw new SyntaxException(PID.BLOCK_ARG_AND_BLOCK_GIVEN, getPosition(((Node)yyVals[-1+yyTop])), lexer.getCurrentLine(), "Both block arg and actual block given.");
                    }
                    yyVal = ((BlockAcceptingNode)yyVals[-1+yyTop]).setIterNode(((IterNode)yyVals[0+yyTop]));
                    ((Node)yyVal).setPosition(getPosition(((Node)yyVals[-1+yyTop])));
    return yyVal;
}
public Object case70_line551(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgn19Node(getPosition(((Token)yyVals[-1+yyTop])), null, ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case354_line1333(Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.commandStart = true;
                    yyVal = ((ArgsNode)yyVals[0+yyTop]);
    return yyVal;
}
public Object case270_line1053(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_colon3(getPosition(((Token)yyVals[-1+yyTop])), (String) ((Token)yyVals[0+yyTop]).getValue());
    return yyVal;
}
public Object case7_line323(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.appendToBlock(((Node)yyVals[-2+yyTop]), support.newline_node(((Node)yyVals[0+yyTop]), getPosition(((Node)yyVals[0+yyTop]))));
    return yyVal;
}
public Object case189_line775(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new OpAsgnNode(getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((Node)yyVals[0+yyTop]), (String) ((Token)yyVals[-2+yyTop]).getValue(), (String) ((Token)yyVals[-1+yyTop]).getValue());
    return yyVal;
}
public Object case3_line295(Object yyVal, Object[] yyVals, int yyTop) {
                  Node node = ((Node)yyVals[-3+yyTop]);

                  if (((RescueBodyNode)yyVals[-2+yyTop]) != null) {
                      node = new RescueNode(getPosition(((Node)yyVals[-3+yyTop])), ((Node)yyVals[-3+yyTop]), ((RescueBodyNode)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]));
                  } else if (((Node)yyVals[-1+yyTop]) != null) {
                      warnings.warn(ID.ELSE_WITHOUT_RESCUE, getPosition(((Node)yyVals[-3+yyTop])), "else without rescue is useless");
                      node = support.appendToBlock(((Node)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]));
                  }
                  if (((Node)yyVals[0+yyTop]) != null) {
                      if (node == null) node = NilImplicitNode.NIL;
                      node = new EnsureNode(getPosition(((Node)yyVals[-3+yyTop])), node, ((Node)yyVals[0+yyTop]));
                  }

                  yyVal = node;
    return yyVal;
}
public Object case371_line1397(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IterNode(getPosition(((Token)yyVals[-4+yyTop])), ((ArgsNode)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), support.getCurrentScope());
                    support.popCurrentScope();
    return yyVal;
}
public Object case352_line1325(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(getPosition(((BlockArgNode)yyVals[0+yyTop])), null, null, null, null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
}
public Object case288_line1114(Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = ((Node)yyVals[-1+yyTop]) == null ? NilImplicitNode.NIL : ((Node)yyVals[-1+yyTop]);
                    yyVal = new WhileNode(getPosition(((Token)yyVals[-6+yyTop])), support.getConditionNode(((Node)yyVals[-4+yyTop])), body);
    return yyVal;
}
public Object case203_line825(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "**", ((Node)yyVals[0+yyTop]), getPosition()), "-@");
    return yyVal;
}
public Object case374_line1416(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((Token)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
}
public Object case305_line1178(Object yyVal, Object[] yyVals, int yyTop) {
                    /* TODO: We should use implicit nil for body, but problem (punt til later)*/
                    Node body = ((Node)yyVals[-1+yyTop]); /*$5 == null ? NilImplicitNode.NIL : $5;*/

                    yyVal = new DefnNode(getPosition(((Token)yyVals[-5+yyTop])), new ArgumentNode(((Token)yyVals[-4+yyTop]).getPosition(), (String) ((Token)yyVals[-4+yyTop]).getValue()), ((ArgsNode)yyVals[-2+yyTop]), support.getCurrentScope(), body);
                    support.popCurrentScope();
                    support.setInDef(false);
    return yyVal;
}
public Object case61_line520(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
}
public Object case207_line841(Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), "|", ((Node)yyVals[0+yyTop]), getPosition());
    return yyVal;
}
					// line 2015 "Ruby19Parser.y"

    /** The parse method use an lexer stream and parse it to an AST node 
     * structure
     */
    public RubyParserResult parse(ParserConfiguration configuration, LexerSource source) {
        support.reset();
        support.setConfiguration(configuration);
        support.setResult(new RubyParserResult());
        
        lexer.reset();
        lexer.setSource(source);
        lexer.setEncoding(configuration.getKCode().getEncoding());
        try {
   //yyparse(lexer, new jay.yydebug.yyAnim("JRuby", 9));
    //yyparse(lexer, new jay.yydebug.yyDebugAdapter());
            yyparse(lexer, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (yyException e) {
            e.printStackTrace();
        }
        
        return support.getResult();
    }

    // +++
    // Helper Methods
    
    void yyerrok() {}

    /**
     * Since we can recieve positions at times we know can be null we
     * need an extra safety net here.
     */
    private ISourcePosition getPosition2(ISourcePositionHolder pos) {
        return pos == null ? lexer.getPosition() : pos.getPosition();
    }

    private ISourcePosition getPosition(ISourcePositionHolder start) {
        return start != null ? lexer.getPosition(start.getPosition()) : lexer.getPosition();
    }

    private ISourcePosition getPosition() {
        return lexer.getPosition();
    }
}
					// line 8111 "-"
